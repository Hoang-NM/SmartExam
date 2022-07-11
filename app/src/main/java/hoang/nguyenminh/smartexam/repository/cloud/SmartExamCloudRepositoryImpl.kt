package hoang.nguyenminh.smartexam.repository.cloud

import hoang.nguyenminh.base.network.unsafeDeserialize
import hoang.nguyenminh.base.serializer.Serializer
import hoang.nguyenminh.smartexam.model.ErrorResponse
import hoang.nguyenminh.smartexam.model.ResultWrapper
import hoang.nguyenminh.smartexam.model.authentication.LoginRequest
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.model.exam.SubmitExamRequest
import hoang.nguyenminh.smartexam.module.network.SmartExamCloudService
import okhttp3.MultipartBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class SmartExamCloudRepositoryImpl @Inject constructor(
    private val serializer: Serializer,
    private val service: SmartExamCloudService
) : SmartExamCloudRepository {

    private suspend fun <T> safeApiCall(call: suspend () -> T): ResultWrapper<T> {
        return try {
            ResultWrapper.Success(call.invoke())
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    try {
                        val code = throwable.code()
                        val errorResponse = throwable.unsafeDeserialize<ErrorResponse>(serializer)
                        ResultWrapper.Error(code, errorResponse, throwable)
                    } catch (exception: Exception) {
                        Timber.e(exception)
                        ResultWrapper.ParserError
                    }
                }
                else -> {
                    ResultWrapper.Error(null, null, throwable)
                }
            }
        }
    }

    override suspend fun login(param: LoginRequest): ResultWrapper<UserInfo> =
        safeApiCall { service.login(param).data }

    override suspend fun getExam(id: Int): ResultWrapper<Exam> =
        safeApiCall { service.getExam(id).data }

    override suspend fun getExamList(): ResultWrapper<List<Exam>> =
        safeApiCall { service.getExamList().data }

    override suspend fun getQuestionList(id: Int): ResultWrapper<List<Question>> =
        safeApiCall { service.getQuestionList(id).data }

    override suspend fun sendExamImage(image: MultipartBody) =
        safeApiCall { service.sendExamImage(image).data }

    override suspend fun submitExam(param: SubmitExamRequest) =
        safeApiCall { service.submitExam(param).data }
}
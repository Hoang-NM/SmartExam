package hoang.nguyenminh.smartexam.repository.cloud

import hoang.nguyenminh.base.network.unsafeDeserialize
import hoang.nguyenminh.base.serializer.Serializer
import hoang.nguyenminh.base.util.IMAGE_SIZE_LIMIT
import hoang.nguyenminh.base.util.getMimeType
import hoang.nguyenminh.base.util.prepareImageFileForUpload
import hoang.nguyenminh.smartexam.model.ErrorResponse
import hoang.nguyenminh.smartexam.model.ResultWrapper
import hoang.nguyenminh.smartexam.model.authentication.LoginRequest
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.model.exam.SubmitExamImageRequest
import hoang.nguyenminh.smartexam.model.exam.SubmitExamRequest
import hoang.nguyenminh.smartexam.module.network.SmartExamCloudService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.File
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
                    serializer.runCatching { throwable.unsafeDeserialize<ErrorResponse>(this) }
                        .fold(
                            onSuccess = {
                                ResultWrapper.Error(throwable.code(), it, throwable)
                            }, onFailure = {
                                Timber.e(it)
                                ResultWrapper.ParserError
                            }
                        )
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

    override suspend fun sendExamImage(params: SubmitExamImageRequest) =
        run {
            val file = prepareImageFileForUpload(File(params.path), IMAGE_SIZE_LIMIT)
            val body = file.asRequestBody(file.getMimeType("image/jpeg").toMediaTypeOrNull())
            val image = MultipartBody.Part.createFormData("images", file.name, body)
            safeApiCall {
                service.sendExamImage(image, params.body.examId, params.body.studentId).data
            }
        }

    override suspend fun submitExam(param: SubmitExamRequest) =
        safeApiCall { service.submitExam(param).data }
}
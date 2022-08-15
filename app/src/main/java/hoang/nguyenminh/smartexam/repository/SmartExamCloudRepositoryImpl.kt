package hoang.nguyenminh.smartexam.repository

import hoang.nguyenminh.base.network.safeDeserialize
import hoang.nguyenminh.base.serializer.Serializer
import hoang.nguyenminh.base.util.IMAGE_SIZE_LIMIT
import hoang.nguyenminh.base.util.fileFromContentUri
import hoang.nguyenminh.base.util.getMimeType
import hoang.nguyenminh.base.util.prepareImageFileForUpload
import hoang.nguyenminh.smartexam.model.BaseResponse
import hoang.nguyenminh.smartexam.model.ErrorResponse
import hoang.nguyenminh.smartexam.model.ResultWrapper
import hoang.nguyenminh.smartexam.model.authentication.LoginRequest
import hoang.nguyenminh.smartexam.model.authentication.UpdateUserInfoRequest
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.model.exam.*
import hoang.nguyenminh.smartexam.model.main.HomeInfo
import hoang.nguyenminh.smartexam.network.SmartExamCloudService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class SmartExamCloudRepositoryImpl @Inject constructor(
    private val serializer: Serializer,
    private val service: SmartExamCloudService
) : SmartExamCloudRepository {

    private suspend fun <T> safeApiCall(call: suspend () -> BaseResponse<T>): ResultWrapper<T> =
        call.runCatching { ResultWrapper.Success(call()) }.fold(
            onSuccess = {
                if (call().isSuccessful()) ResultWrapper.Success(call().data)
                else ResultWrapper.ServerError(call().code, call().message)
            }, onFailure = { throwable ->
                Timber.e(throwable)
                when (throwable) {
                    is IOException -> {
                        Timber.e(throwable.localizedMessage)
                        ResultWrapper.NetworkError
                    }
                    is HttpException -> {
                        throwable.safeDeserialize<ErrorResponse>(serializer)?.let {
                            ResultWrapper.Error(throwable.code(), it, throwable)
                        } ?: ResultWrapper.ParserError
                    }
                    else -> {
                        ResultWrapper.Error(null, null, throwable)
                    }
                }
            }
        )

    override suspend fun login(param: LoginRequest): ResultWrapper<UserInfo> =
        safeApiCall { service.login(param) }

    override suspend fun getHomeInfo(param: Int): ResultWrapper<HomeInfo> =
        safeApiCall { service.getHomeInfo(param) }

    override suspend fun getExam(id: Int): ResultWrapper<Exam> =
        safeApiCall { service.getExam(id) }

    override suspend fun getExamList(param: GetExamListRequest): ResultWrapper<List<Exam>> =
        safeApiCall { service.getExamList(param.build()) }

    override suspend fun getQuestionList(id: Int): ResultWrapper<List<Question>> =
        safeApiCall { service.getQuestionList(id) }

    override suspend fun sendExamImage(params: SubmitExamImageRequest) =
        run {
            val rawFile = fileFromContentUri(params.uri)
            val processedFile = prepareImageFileForUpload(rawFile, IMAGE_SIZE_LIMIT)
            val body = processedFile.asRequestBody(
                processedFile.getMimeType("image/jpeg").toMediaTypeOrNull()
            )
            val image = MultipartBody.Part.createFormData("image", processedFile.name, body)
            safeApiCall {
                service.sendExamImage(image, params.query.examId, params.query.studentId)
            }
        }

    override suspend fun submitExam(param: SubmitExamRequest) =
        safeApiCall { service.submitExam(param) }

    override suspend fun getExamAnswer(param: GetExamAnswerRequest): ResultWrapper<ExamAnswer> =
        safeApiCall { service.getExamAnswer(param.build()) }

    override suspend fun updateUserInfo(params: UpdateUserInfoRequest) =
        safeApiCall { service.updateUserInfo(params) }
}
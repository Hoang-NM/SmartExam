package hoang.nguyenminh.smartexam.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import hoang.nguyenminh.smartexam.exception.ErrorResponseException

object ResponseCode {
    const val OK = 200
}

data class BaseResponse<T>(
    @SerializedName("errCode") @Expose val code: Int,
    @SerializedName("errMessage") @Expose val message: String,
    @SerializedName("data") @Expose val data: T
) {
    fun isSuccessful(): Boolean = ResponseCode.OK == code

    fun successOrThrows() {
        if (!isSuccessful()) {
            failure()
        }
    }

    fun failure(): Nothing =
        throw ErrorResponseException(message, null, null, ErrorResponse(this))
}

sealed class ResultWrapper<out T> {

    data class Success<out T>(val value: T) : ResultWrapper<T>()

    data class Error(val errorCode: Int?, val error: ErrorResponse?, val throwable: Throwable) :
        ResultWrapper<Nothing>()

    object ParserError : ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()
}

data class ErrorResponse(
    @SerializedName("errCode") @Expose val code: Int,
    @SerializedName("errMessage") @Expose val message: String
) {
    constructor(response: BaseResponse<*>) : this(response.code, response.message)
}
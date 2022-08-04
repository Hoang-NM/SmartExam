package hoang.nguyenminh.smartexam.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

object ResponseCode {
    const val OK = 0
}

data class BaseResponse<T>(
    @SerializedName("errCode") @Expose val code: Int,
    @SerializedName("errMessage", alternate = ["message"]) @Expose val message: String,
    @SerializedName("data") @Expose val data: T
) {

    fun isSuccessful() = code == ResponseCode.OK
}

sealed class ResultWrapper<out T> {

    data class Success<out T>(val value: T) : ResultWrapper<T>()

    data class Error(val errorCode: Int?, val error: ErrorResponse?, val throwable: Throwable) :
        ResultWrapper<Nothing>()

    data class ServerError<out T>(val code: Int, val message: String) : ResultWrapper<T>()

    object ParserError : ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()
}

data class ErrorResponse(
    @SerializedName("errCode") @Expose val code: Int,
    @SerializedName("errMessage") @Expose val message: String?
)
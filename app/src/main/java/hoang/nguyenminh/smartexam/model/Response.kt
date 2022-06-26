package hoang.nguyenminh.smartexam.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

object ResponseCode {
    const val OK = 200
}

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @Json(name = "errCode") val code: Int,
    @Json(name = "errMessage") val message: String,
    @Json(name = "data") val data: T
) {
    fun isSuccess(): Boolean = ResponseCode.OK == code
}
package hoang.nguyenminh.smartexam.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

object ResponseCode {
    const val OK = 200
}

data class BaseResponse<T>(
    @SerializedName("errCode") @Expose val code: Int,
    @SerializedName("errMessage") @Expose val message: String,
    @SerializedName("data") @Expose val data: T
) {
    fun isSuccess(): Boolean = ResponseCode.OK == code
}
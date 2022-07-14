package hoang.nguyenminh.smartexam.model.authentication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username") @Expose var username: String = "",
    @SerializedName("password") @Expose var password: String = "123456"
)

data class UserInfo(
    @SerializedName("id") @Expose val userId: Int,
    @SerializedName("email") @Expose val email: Int,
)
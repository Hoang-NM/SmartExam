package hoang.nguyenminh.smartexam.model.authentication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

enum class Role(val id: Int) {
    UNKNOWN(-1), ADMIN(0), STUDENT(1), TEACHER(2);

    companion object {
        fun fromIntConstant(id: Int): Role? = values().firstOrNull {
            it.id == id
        }
    }
}

data class LoginRequest(
    @SerializedName("email") @Expose var username: String = "",
    @SerializedName("password") @Expose var password: String = "123456"
)

data class UserInfo(
    @SerializedName("id") @Expose val userId: Int,
    @SerializedName("email") @Expose val email: Int,
    @SerializedName("roleId") @Expose val roleId: Int,
) {
    fun getRole(): Role = Role.fromIntConstant(roleId) ?: Role.UNKNOWN
}
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
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("email") @Expose val email: String,
    @SerializedName("firstName") @Expose val firstName: String? = null,
    @SerializedName("lastName") @Expose val lastName: String? = null,
    @SerializedName("class") @Expose val className: String? = null,
    @SerializedName("roleId") @Expose val roleId: Int,
    @SerializedName("classId") @Expose val classId: Int? = 0,
) {
    fun getName() = "$firstName $lastName"

    fun getRole(): Role = Role.fromIntConstant(roleId) ?: Role.UNKNOWN
}

data class UpdateUserInfoRequest(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("firstName") @Expose val firstName: String? = null,
    @SerializedName("lastName") @Expose val lastName: String? = null,
    @SerializedName("address") @Expose val address: String? = null,
    @SerializedName("class") @Expose val className: String? = null,
    @SerializedName("gender") @Expose val gender: Int? = Gender.MALE.value,
    @SerializedName("dateOfBirth") @Expose val dob: String? = null,
    @SerializedName("phoneNumber") @Expose val phoneNumber: String? = null,
)

enum class Gender(val value: Int) {
    MALE(0), FEMALE(1), OTHER(2);

    companion object {
        fun fromIntConstant(value: Int): Gender? = values().firstOrNull {
            it.value == value
        }
    }
}
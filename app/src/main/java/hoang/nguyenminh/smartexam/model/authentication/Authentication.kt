package hoang.nguyenminh.smartexam.model.authentication

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") @Expose var username: String = "",
    @SerializedName("password") @Expose var password: String = "123456"
)

data class UserInfo(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("email") @Expose val email: String,
    @SerializedName("firstName") @Expose val firstName: String? = null,
    @SerializedName("lastName") @Expose val lastName: String? = null,
    @SerializedName("address") @Expose val address: String? = null,
    @SerializedName("class") @Expose val className: String? = null,
    @SerializedName("gender") @Expose val gender: Int? = Gender.MALE.value,
    @SerializedName("dateOfBirth") @Expose val dob: String? = null,
    @SerializedName("phoneNumber") @Expose val phoneNumber: String? = null,
) {
    fun getName() = "$firstName $lastName"

    fun toUpdateRequest(): UpdateUserInfoRequest = UpdateUserInfoRequest(
        id, firstName, lastName, address, className, gender, dob, phoneNumber
    )

    fun fromUpdateRequest(request: UpdateUserInfoRequest): UserInfo = copy(
        id = request.id,
        email = email,
        firstName = request.firstName,
        lastName = request.lastName,
        address = request.address,
        className = request.className,
        gender = request.gender,
        dob = request.dob,
        phoneNumber = request.phoneNumber
    )
}

data class UpdateUserInfoRequest(
    @SerializedName("id") @Expose val id: Int = 0,
    @SerializedName("firstName") @Expose var firstName: String? = null,
    @SerializedName("lastName") @Expose var lastName: String? = null,
    @SerializedName("address") @Expose var address: String? = null,
    @SerializedName("class") @Expose val className: String? = null,
    @SerializedName("gender") @Expose var gender: Int? = Gender.MALE.value,
    @SerializedName("dateOfBirth") @Expose var dob: String? = null,
    @SerializedName("phoneNumber") @Expose var phoneNumber: String? = null,
) {
    fun fromUserInfo(user: UserInfo): UpdateUserInfoRequest = user.toUpdateRequest()
}

enum class Gender(val value: Int) {
    MALE(0), FEMALE(1), OTHER(2);

    companion object {
        fun fromIntConstant(value: Int): Gender? = values().firstOrNull {
            it.value == value
        }
    }
}
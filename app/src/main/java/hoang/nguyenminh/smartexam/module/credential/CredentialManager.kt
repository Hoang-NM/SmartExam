package hoang.nguyenminh.smartexam.module.credential

interface CredentialManager {

    fun saveToken(token: String?)

    fun getToken(): String?

}
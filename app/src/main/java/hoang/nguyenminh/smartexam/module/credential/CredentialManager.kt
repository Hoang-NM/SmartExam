package hoang.nguyenminh.smartexam.module.credential

import hoang.nguyenminh.smartexam.model.authentication.UserInfo

interface CredentialManager {

    fun saveAuthenticationInfo(info: UserInfo)

    fun getAuthenticationInfo(): UserInfo?

    fun saveToken(token: String?)

    fun getToken(): String?

    fun clearAuthenticationInfo()
}
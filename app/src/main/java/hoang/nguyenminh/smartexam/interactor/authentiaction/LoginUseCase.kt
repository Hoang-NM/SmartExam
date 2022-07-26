package hoang.nguyenminh.smartexam.interactor.authentiaction

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.model.authentication.LoginRequest
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.module.credential.CredentialManager
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<UserInfo, LoginRequest>() {

    @Inject
    lateinit var credentialManager: CredentialManager

    override suspend fun run(params: LoginRequest): UserInfo =
        repository.login(params).apply {
            credentialManager.saveAuthenticationInfo(this)
        }
}
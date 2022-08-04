package hoang.nguyenminh.smartexam.interactor.authentiaction

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.interactor.userInfo
import hoang.nguyenminh.smartexam.model.ResultWrapper
import hoang.nguyenminh.smartexam.model.authentication.LoginRequest
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.repository.SmartExamCloudRepository
import hoang.nguyenminh.smartexam.util.module.credential.CredentialManager
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<ResultWrapper<UserInfo>, LoginRequest>() {

    @Inject
    lateinit var credentialManager: CredentialManager

    override suspend fun run(params: LoginRequest): ResultWrapper<UserInfo> {
        credentialManager.saveAuthenticationInfo(userInfo())
        return ResultWrapper.Success(userInfo())
    }
}
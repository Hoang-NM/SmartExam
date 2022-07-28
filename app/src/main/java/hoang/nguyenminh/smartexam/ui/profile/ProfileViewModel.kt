package hoang.nguyenminh.smartexam.ui.profile

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.base.util.postToBus
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.authentiaction.UpdateUserInfoUseCase
import hoang.nguyenminh.smartexam.model.UpdateUserInfoEvent
import hoang.nguyenminh.smartexam.model.authentication.UpdateUserInfoRequest
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.module.credential.CredentialManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var credentialManager: CredentialManager

    @Inject
    lateinit var useCase: UpdateUserInfoUseCase

    private val request by lazy {
        UpdateUserInfoRequest()
    }

    val flowOfUserInfo = MutableStateFlow<UserInfo?>(null)

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        flowOfUserInfo.value ?: run {
            flowOfUserInfo.value = credentialManager.getAuthenticationInfo()?.also {
                request.fromUserInfo(it)
            }
        }
    }

    fun saveUserInfo() = viewModelScope.launch {
        execute(useCase, request, onSuccess = {
            flowOfUserInfo.value?.let {
                credentialManager.saveAuthenticationInfo(it.fromUpdateRequest(request))
                UpdateUserInfoEvent(it).postToBus()
            }
        })
    }
}
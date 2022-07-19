package hoang.nguyenminh.smartexam.ui.profile

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
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

    val flowOfUserInfo = MutableStateFlow<UserInfo?>(null)

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        flowOfUserInfo.value ?: run {
            flowOfUserInfo.value = credentialManager.getAuthenticationInfo()
        }
    }

    fun clearAuthenticationInfo() = viewModelScope.launch {
        credentialManager.clearAuthenticationInfo()
    }
}
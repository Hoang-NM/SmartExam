package hoang.nguyenminh.smartexam.ui.authentication

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.module.credential.CredentialManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var credentialManager: CredentialManager

    var isLoading = false

    val flowOfAuthenticationInfo = MutableStateFlow<UserInfo?>(null)

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        runBlocking {
            delay(500L)
//            launch {
//                flowOfAuthenticationInfo.value = credentialManager.getAuthenticationInfo()
//            }.join()
            isLoading = true
        }
    }
}
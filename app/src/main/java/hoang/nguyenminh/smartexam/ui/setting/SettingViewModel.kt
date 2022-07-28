package hoang.nguyenminh.smartexam.ui.setting

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.base.scene.Scene
import hoang.nguyenminh.base.util.subscribeBusEntireLifecycle
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.model.UpdateUserInfoEvent
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.module.credential.CredentialManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(application: Application) :
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

    override fun onAttachScene(scene: Scene) {
        super.onAttachScene(scene)
        scene.lifecycleOwner().subscribeBusEntireLifecycle(this)
    }

    fun clearAuthenticationInfo() = viewModelScope.launch {
        credentialManager.clearAuthenticationInfo()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveUserInfoEvent(event: UpdateUserInfoEvent) {
        flowOfUserInfo.value = event.userInfo
    }
}
package hoang.nguyenminh.smartexam.ui.authentication.register

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.base.scene.BaseAndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(application: Application) :
    BaseAndroidViewModel(application) {
    val userName = MutableStateFlow("")
    val password = MutableStateFlow("")
    val reEnterPassword = MutableStateFlow("")

    val enabled = combine(
        flow = userName,
        flow2 = password,
        flow3 = reEnterPassword
    ) { userName, password, reEnterPassword ->
        userName.isNotBlank() && password.isNotBlank() && reEnterPassword.isNotBlank() && password == reEnterPassword
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)
}
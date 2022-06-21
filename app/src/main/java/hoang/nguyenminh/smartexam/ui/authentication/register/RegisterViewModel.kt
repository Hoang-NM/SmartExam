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

    private val flowOfUserName = MutableStateFlow("")

    private val flowOfPassword = MutableStateFlow("")

    private val flowOfReEnterPassword = MutableStateFlow("")

    private val enabled = combine(
        flow = flowOfUserName,
        flow2 = flowOfPassword,
        flow3 = flowOfReEnterPassword
    ) { userName, password, reEnterPassword ->
        userName.isNotBlank() && password.isNotBlank() && reEnterPassword.isNotBlank() && password == reEnterPassword
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun getUserName(): MutableStateFlow<String> = flowOfUserName

    fun getPassword(): MutableStateFlow<String> = flowOfPassword

    fun getReEnterPassword(): MutableStateFlow<String> = flowOfReEnterPassword
}
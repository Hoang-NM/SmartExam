package hoang.nguyenminh.smartexam.ui.authentication.login

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
class LoginViewModel @Inject constructor(application: Application) :
    BaseAndroidViewModel(application) {

    private val flowOfUserName = MutableStateFlow("hoang")

    private val flowOfPassword = MutableStateFlow("123456")

    private val flowOfEnabled =
        combine(flow = flowOfUserName, flow2 = flowOfPassword) { userName, password ->
            userName.isNotBlank() && password.isNotBlank()
        }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun getUserName(): MutableStateFlow<String> = flowOfUserName

    fun getPassword(): MutableStateFlow<String> = flowOfPassword
}
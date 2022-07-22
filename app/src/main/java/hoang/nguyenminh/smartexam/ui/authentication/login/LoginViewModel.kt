package hoang.nguyenminh.smartexam.ui.authentication.login

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.authentiaction.LoginUseCase
import hoang.nguyenminh.smartexam.model.authentication.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var useCase: LoginUseCase

    private val request by lazy {
        LoginRequest()
    }

    private val flowOfUserName = MutableStateFlow("student1@gmail.com")

    private val flowOfPassword = MutableStateFlow("123456")

    private val flowOfEnabled =
        combine(flow = flowOfUserName, flow2 = flowOfPassword) { userName, password ->
            userName.isNotBlank() && password.isNotBlank()
        }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun getUserName(): MutableStateFlow<String> = flowOfUserName

    fun getPassword(): MutableStateFlow<String> = flowOfPassword

    fun login(onSuccess: () -> Unit) {
        request.apply {
            username = flowOfUserName.value
            password = flowOfPassword.value
        }
        execute(useCase, request, onSuccess = { onSuccess() })
    }
}
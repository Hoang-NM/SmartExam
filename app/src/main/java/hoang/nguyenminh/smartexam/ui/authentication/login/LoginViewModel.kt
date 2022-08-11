package hoang.nguyenminh.smartexam.ui.authentication.login

import android.app.Application
import android.util.Patterns
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

    private val flowOfEmail = MutableStateFlow("student1@gmail.com")

    private val flowOfPassword = MutableStateFlow("123456")

    private val flowOfEnabled =
        combine(flow = flowOfEmail, flow2 = flowOfPassword) { userName, password ->
            userName.isNotBlank() && password.isNotBlank()
        }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun getEmail(): MutableStateFlow<String> = flowOfEmail

    fun getPassword(): MutableStateFlow<String> = flowOfPassword

    fun isEnabled() = flowOfEnabled

    fun login(onSuccess: () -> Unit) {
        if (!flowOfEmail.value.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            notifyError("Please enter a valid email address")
            return
        }
        if (flowOfPassword.value.length < 6) {
            notifyError("Password must contains at least 6 characters")
            return
        }
        request.apply {
            username = flowOfEmail.value
            password = flowOfPassword.value
        }
        execute(useCase, request, onSuccess = { onSuccess() })
    }
}
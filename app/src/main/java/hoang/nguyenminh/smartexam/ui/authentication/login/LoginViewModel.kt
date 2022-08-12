package hoang.nguyenminh.smartexam.ui.authentication.login

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.authentiaction.LoginUseCase
import hoang.nguyenminh.smartexam.model.authentication.LoginRequest
import kotlinx.coroutines.flow.*
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

    private val flowOfErrorEmail = MutableStateFlow<String?>(null)

    private val flowOfPassword = MutableStateFlow("123456")

    private val flowOfErrorPassword = MutableStateFlow<String?>(null)

    private val flowOfEnabled =
        combine(flow = flowOfEmail, flow2 = flowOfPassword) { userName, password ->
            userName.isNotBlank() && password.isNotBlank()
        }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun getEmail(): MutableStateFlow<String> = flowOfEmail

    fun getErrorEmail(): StateFlow<String?> = flowOfErrorEmail

    fun getPassword(): MutableStateFlow<String> = flowOfPassword

    fun getErrorPassword(): StateFlow<String?> = flowOfErrorPassword

    fun isEnabled() = flowOfEnabled

    fun login(onSuccess: () -> Unit) {
        flowOfErrorEmail.value = null
        flowOfErrorPassword.value = null
        if (!flowOfEmail.value.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            flowOfErrorEmail.value = resource.getString(R.string.message_invalid_email)
            return
        }
        if (flowOfPassword.value.length < 6) {
            flowOfErrorPassword.value = resource.getString(R.string.message_invalid_password)
            return
        }
        request.apply {
            username = flowOfEmail.value
            password = flowOfPassword.value
        }
        execute(useCase, request, onSuccess = { onSuccess() })
    }
}
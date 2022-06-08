package hoang.nguyenminh.smartexam.ui.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val smartExamCloudRepository: SmartExamCloudRepository
) : ViewModel() {
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
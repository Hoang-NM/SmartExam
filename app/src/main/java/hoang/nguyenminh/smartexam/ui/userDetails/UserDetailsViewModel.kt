package hoang.nguyenminh.smartexam.ui.userDetails

import androidx.databinding.ObservableParcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hoang.nguyenminh.smartexam.domain.UserDetails
import hoang.nguyenminh.smartexam.repository.local.SmartExamLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val smartExamLocalRepository: SmartExamLocalRepository
) : ViewModel() {

    val userDetails = ObservableParcelable(UserDetails())

    fun getUserDetails(user: String) = smartExamLocalRepository.getUserDetails(user)

    fun refreshUserDetails(user: String) = viewModelScope.launch(Dispatchers.IO) {
        smartExamLocalRepository.refreshUserDetails(user)
    }

}
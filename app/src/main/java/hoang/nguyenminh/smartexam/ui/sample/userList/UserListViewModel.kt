package hoang.nguyenminh.smartexam.ui.sample.userList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.repository.local.SmartExamLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val smartExamLocalRepository: SmartExamLocalRepository
) : ViewModel() {

    val data = smartExamLocalRepository.users

    init {
        viewModelScope.launch(Dispatchers.IO) {
            smartExamLocalRepository.refreshUserList()
        }
    }

}
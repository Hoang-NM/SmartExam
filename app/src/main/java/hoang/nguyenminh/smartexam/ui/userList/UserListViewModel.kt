package hoang.nguyenminh.smartexam.ui.userList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hoang.nguyenminh.smartexam.repository.local.UserListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userListRepository: UserListRepository
) : ViewModel() {

    val data = userListRepository.users

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userListRepository.refreshUserList()
        }
    }

}
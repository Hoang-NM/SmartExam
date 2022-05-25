package hoang.nguyenminh.smartexam.repository.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import hoang.nguyenminh.smartexam.database.UsersDatabase
import hoang.nguyenminh.smartexam.database.asDomainModel
import hoang.nguyenminh.smartexam.domain.UserListItem
import hoang.nguyenminh.smartexam.network.UserListService
import hoang.nguyenminh.smartexam.network.model.asDatabaseModel
import timber.log.Timber
import javax.inject.Inject

class UserListRepository @Inject constructor(
    private val userListService: UserListService,
    private val database: UsersDatabase
) {

    val users: LiveData<List<UserListItem>> =
        Transformations.map(database.usersDao.getDatabaseUsers()) {
            it.asDomainModel()
        }

    suspend fun refreshUserList() {
        try {
            val users = userListService.getUserList()
            database.usersDao.insertAll(users.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }
}
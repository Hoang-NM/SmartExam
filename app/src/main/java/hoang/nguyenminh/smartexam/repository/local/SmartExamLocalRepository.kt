package hoang.nguyenminh.smartexam.repository.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import hoang.nguyenminh.smartexam.database.UsersDatabase
import hoang.nguyenminh.smartexam.database.asDomainModel
import hoang.nguyenminh.smartexam.domain.UserDetails
import hoang.nguyenminh.smartexam.domain.UserListItem
import hoang.nguyenminh.smartexam.network.SmartExamLocalService
import hoang.nguyenminh.smartexam.model.asDatabaseModel
import timber.log.Timber
import javax.inject.Inject

class SmartExamLocalRepository @Inject constructor(
    private val smartExamLocalService: SmartExamLocalService,
    private val database: UsersDatabase
) {

    val users: LiveData<List<UserListItem>> =
        Transformations.map(database.usersDao.getDatabaseUsers()) {
            it.asDomainModel()
        }

    suspend fun refreshUserList() {
        try {
            val users = smartExamLocalService.getUserList()
            database.usersDao.insertAll(users.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

    fun getUserDetails(user: String): LiveData<UserDetails> {
        return Transformations.map(database.usersDao.getUserDetails(user)) {
            it?.asDomainModel()
        }
    }


    suspend fun refreshUserDetails(user: String) {
        try {
            val userDetails = smartExamLocalService.getUserDetails(user)
            database.usersDao.insertUserDetails(userDetails.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }
}
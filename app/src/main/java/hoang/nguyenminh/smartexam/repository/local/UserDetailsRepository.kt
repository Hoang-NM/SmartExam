package hoang.nguyenminh.smartexam.repository.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import hoang.nguyenminh.smartexam.database.UsersDatabase
import hoang.nguyenminh.smartexam.database.asDomainModel
import hoang.nguyenminh.smartexam.domain.UserDetails
import hoang.nguyenminh.smartexam.network.UserDetailsService
import hoang.nguyenminh.smartexam.network.model.asDatabaseModel
import timber.log.Timber
import javax.inject.Inject

class UserDetailsRepository @Inject constructor(
    private val userDetailsService: UserDetailsService,
    private val database: UsersDatabase
) {

    fun getUserDetails(user: String): LiveData<UserDetails> {
        return Transformations.map(database.usersDao.getUserDetails(user)) {
            it?.asDomainModel()
        }
    }


    suspend fun refreshUserDetails(user: String) {
        try {
            val userDetails = userDetailsService.getUserDetails(user)
            database.usersDao.insertUserDetails(userDetails.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

}
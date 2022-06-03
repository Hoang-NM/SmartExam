package hoang.nguyenminh.smartexam.network

import hoang.nguyenminh.smartexam.model.user.NetworkUserDetails
import hoang.nguyenminh.smartexam.model.user.NetworkUserListItem
import retrofit2.http.GET
import retrofit2.http.Path

interface SmartExamLocalService {

    @GET("/repos/square/retrofit/stargazers")
    suspend fun getUserList(): List<NetworkUserListItem>

    @GET("/users/{user}")
    suspend fun getUserDetails(@Path("user") user: String): NetworkUserDetails
}
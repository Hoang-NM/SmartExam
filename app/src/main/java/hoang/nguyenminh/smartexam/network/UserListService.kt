package hoang.nguyenminh.smartexam.network

import hoang.nguyenminh.smartexam.network.model.NetworkUserListItem
import retrofit2.http.GET

interface UserListService {

    @GET("/repos/square/retrofit/stargazers")
    suspend fun getUserList(): List<NetworkUserListItem>
}
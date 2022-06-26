package hoang.nguyenminh.smartexam.model.user


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import hoang.nguyenminh.smartexam.database.DatabaseUserListItem

data class NetworkUserListItem(
    @SerializedName("avatar_url")
    @Expose val avatarUrl: String,
    @SerializedName("events_url")
    @Expose val eventsUrl: String,
    @SerializedName("followers_url")
    @Expose val followersUrl: String,
    @SerializedName("following_url")
    @Expose val followingUrl: String,
    @SerializedName("gists_url")
    @Expose val gistsUrl: String,
    @SerializedName("gravatar_id")
    @Expose val gravatarId: String,
    @SerializedName("html_url")
    @Expose val htmlUrl: String,
    @SerializedName("id")
    @Expose val id: Int,
    @SerializedName("login")
    @Expose val login: String,
    @SerializedName("node_id")
    @Expose val nodeId: String,
    @SerializedName("organizations_url")
    @Expose val organizationsUrl: String,
    @SerializedName("received_events_url")
    @Expose val receivedEventsUrl: String,
    @SerializedName("repos_url")
    @Expose val reposUrl: String,
    @SerializedName("site_admin")
    @Expose val siteAdmin: Boolean,
    @SerializedName("starred_url")
    @Expose val starredUrl: String,
    @SerializedName("subscriptions_url")
    @Expose val subscriptionsUrl: String,
    @SerializedName("type")
    @Expose val type: String,
    @SerializedName("url")
    @Expose val url: String
)

fun List<NetworkUserListItem>.asDatabaseModel(): List<DatabaseUserListItem> {
    return map {
        DatabaseUserListItem(
            id = it.id,
            avatar = it.avatarUrl,
            username = it.login
        )
    }
}
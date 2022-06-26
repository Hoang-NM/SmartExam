package hoang.nguyenminh.smartexam.model.user


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import hoang.nguyenminh.smartexam.database.DatabaseUserDetails

data class NetworkUserDetails(
    @SerializedName("avatar_url") @Expose val avatarUrl: String,
    @SerializedName("bio")
    @Expose val bio: String? = null,
    @SerializedName("blog")
    @Expose val blog: String? = null,
    @SerializedName("company")
    @Expose val company: String? = null,
    @SerializedName("created_at")
    @Expose val createdAt: String? = null,
    @SerializedName("email")
    @Expose val email: String? = null,
    @SerializedName("events_url")
    @Expose val eventsUrl: String? = null,
    @SerializedName("followers")
    @Expose val followers: Int? = null,
    @SerializedName("followers_url")
    @Expose val followersUrl: String? = null,
    @SerializedName("following")
    @Expose val following: Int? = null,
    @SerializedName("following_url")
    @Expose val followingUrl: String? = null,
    @SerializedName("gists_url")
    @Expose val gistsUrl: String? = null,
    @SerializedName("gravatar_id")
    @Expose val gravatarId: String? = null,
    @SerializedName("hireable")
    @Expose val hireable: Boolean? = null,
    @SerializedName("html_url")
    @Expose val htmlUrl: String? = null,
    @SerializedName("id")
    @Expose val id: Int,
    @SerializedName("location")
    @Expose val location: String? = null,
    @SerializedName("login")
    @Expose val login: String,
    @SerializedName("name")
    @Expose val name: String? = null,
    @SerializedName("node_id")
    @Expose val nodeId: String? = null,
    @SerializedName("organizations_url")
    @Expose val organizationsUrl: String? = null,
    @SerializedName("public_gists")
    @Expose val publicGists: Int? = null,
    @SerializedName("public_repos")
    @Expose val publicRepos: Int? = null,
    @SerializedName("received_events_url")
    @Expose val receivedEventsUrl: String? = null,
    @SerializedName("repos_url")
    @Expose val reposUrl: String? = null,
    @SerializedName("site_admin")
    @Expose val siteAdmin: Boolean? = null,
    @SerializedName("starred_url")
    @Expose val starredUrl: String? = null,
    @SerializedName("subscriptions_url")
    @Expose val subscriptionsUrl: String? = null,
    @SerializedName("twitter_username")
    @Expose val twitterUsername: String? = null,
    @SerializedName("type")
    @Expose val type: String? = null,
    @SerializedName("updated_at")
    @Expose val updatedAt: String? = null,
    @SerializedName("url")
    @Expose val url: String? = null
)

fun NetworkUserDetails.asDatabaseModel(): DatabaseUserDetails {
    return DatabaseUserDetails(
        user = login,
        avatar = avatarUrl,
        name = name ?: "",
        userSince = createdAt ?: "",
        location = location ?: ""
    )

}
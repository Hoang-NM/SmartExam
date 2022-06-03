package hoang.nguyenminh.smartexam.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetails(
    val user: String? = "",
    val avatar: String? = "",
    val name: String? = "",
    val userSince: String? = "",
    val location: String? = ""
) : Parcelable
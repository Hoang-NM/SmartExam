package hoang.nguyenminh.smartexam.model.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MenuItem(val id: String, @DrawableRes val icon: Int, @StringRes val label: Int)
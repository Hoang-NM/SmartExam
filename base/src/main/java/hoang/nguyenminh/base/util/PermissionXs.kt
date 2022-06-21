package hoang.nguyenminh.base.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.isAllPermissionsGranted(vararg permission: String): Boolean {
    if (permission.isEmpty()) {
        throw IllegalArgumentException("Permission cannot be empty")
    }
    return permission.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
}
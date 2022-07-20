package hoang.nguyenminh.base.scene

import android.content.res.Resources
import androidx.lifecycle.LifecycleOwner
import hoang.nguyenminh.base.util.ConfirmRequest

interface Scene {
    fun getSceneResource(): Resources

    fun lifecycleOwner(): LifecycleOwner

    fun showMessage(request: ConfirmRequest)

    fun toast(message: String?)
}
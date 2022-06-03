package hoang.nguyenminh.base.scene

import android.content.res.Resources
import androidx.lifecycle.LifecycleOwner

interface Scene {
    fun getSceneResource(): Resources

    fun lifecycleOwner(): LifecycleOwner
}

interface IViewModel {

}
package hoang.nguyenminh.base.scene

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel

interface IViewModel {
    fun onBind(args: Bundle?)

    fun onAttachScene(scene: Scene)

    fun onReady()

    fun onDetachScene(scene: Scene)

    fun onUnBind()
}

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application),
    IViewModel {

    override fun onBind(args: Bundle?) {}

    override fun onAttachScene(scene: Scene) {}

    override fun onReady() {}

    override fun onDetachScene(scene: Scene) {}

    override fun onUnBind() {}
}
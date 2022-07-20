package hoang.nguyenminh.base.scene

import android.app.Application
import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import hoang.nguyenminh.base.util.ConfirmRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

interface IViewModel {
    fun onBind(args: Bundle?)

    fun onAttachScene(scene: Scene)

    fun onReady()

    fun onDetachScene(scene: Scene)

    fun onUnBind()

    fun getConfirmEvent(): Flow<ConfirmRequest>

    fun getToastEvent(): Flow<String>
}

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application),
    IViewModel {

    lateinit var sceneRef: WeakReference<Scene>

    lateinit var flowOfConfirmEvent: MutableSharedFlow<ConfirmRequest>

    lateinit var flowOfToast: MutableSharedFlow<String>

    override fun onBind(args: Bundle?) {}

    override fun onAttachScene(scene: Scene) {
        sceneRef.get() ?: scene.also {
            sceneRef = WeakReference(it)
        }
    }

    override fun onReady() {}

    override fun onDetachScene(scene: Scene) {}

    override fun onUnBind() {}

    protected val resource: Resources
        get() = getApplication<Application>().resources

    protected open fun requestConfirm(request: ConfirmRequest) {
        viewModelScope.launch {
            flowOfConfirmEvent.emit(request)
        }
    }

    protected open fun toast(@StringRes resId: Int) {
        viewModelScope.launch {
            flowOfToast.emit(resource.getString(resId))
        }
    }

    protected open fun toast(text: String) {
        viewModelScope.launch {
            flowOfToast.emit(text)
        }
    }

    override fun getConfirmEvent(): Flow<ConfirmRequest> = flowOfConfirmEvent

    override fun getToastEvent(): Flow<String> = flowOfToast
}
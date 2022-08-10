package hoang.nguyenminh.base.scene

import android.app.Application
import android.content.res.Resources
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import hoang.nguyenminh.base.util.ConfirmRequest
import hoang.nguyenminh.base.util.CountDelegation
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

interface IViewModel {
    fun onBind(args: Bundle?)

    fun onAttachScene(scene: Scene)

    fun onReady()

    fun onDetachScene(scene: Scene)

    fun onUnBind()

    fun getTaskCount(): StateFlow<Int>

    fun getConfirmEvent(): Flow<ConfirmRequest>

    fun getToastEvent(): Flow<String>
}

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application),
    IViewModel {

    protected var sceneRef = WeakReference<Scene>(null)
        private set

    private val flowOfConfirmEvent = MutableSharedFlow<ConfirmRequest>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val flowOfToast = MutableSharedFlow<String>(
        extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var taskCount = CountDelegation()

    override fun onBind(args: Bundle?) {}

    override fun onAttachScene(scene: Scene) {
        sceneRef = WeakReference(scene)
    }

    override fun onReady() {}

    override fun onDetachScene(scene: Scene) {
        sceneRef = WeakReference(null)
    }

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

    protected open fun notifyTaskStart() {
        taskCount.increase()
    }

    protected open fun notifyTaskFinish() {
        taskCount.decrease()
    }

    protected open fun notifyAllTaskCleared() {
        taskCount.clear()
    }

    override fun getTaskCount(): StateFlow<Int> = taskCount.flowOfCount

    override fun getConfirmEvent(): Flow<ConfirmRequest> = flowOfConfirmEvent

    override fun getToastEvent(): Flow<String> = flowOfToast
}
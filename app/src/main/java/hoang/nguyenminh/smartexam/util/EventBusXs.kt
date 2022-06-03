package hoang.nguyenminh.smartexam.util

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.greenrobot.eventbus.EventBus

val busInstance: EventBus by lazy {
    EventBus.getDefault()
}

fun Any.postToBus() {
    busInstance.post(this)
}

fun Any.postStickyToBus() {
    busInstance.postSticky(this)
}

fun LifecycleOwner.subscribeBusEntireLifecycle(subscribe: Any): LifecycleObserver {
    return object : DefaultLifecycleObserver {

        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            busInstance.register(subscribe)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            busInstance.unregister(subscribe)
        }
    }.also {
        lifecycle.addObserver(it)
    }
}
package hoang.nguyenminh.base.util

import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

class WeakReferenceLazy<T>(private val initialize: () -> T) {

    private var weakRef: WeakReference<T> = WeakReference(null)

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
        weakRef.get() ?: initialize().also { weakRef = WeakReference(it) }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (value == null) weakRef.clear()
        else weakRef = WeakReference(value)
    }
}

fun <T> weakLazy(initialize: () -> T) = WeakReferenceLazy(initialize)
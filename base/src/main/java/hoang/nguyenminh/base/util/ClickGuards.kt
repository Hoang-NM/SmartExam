package hoang.nguyenminh.base.util

import android.os.SystemClock
import android.view.View
import androidx.databinding.BindingAdapter

abstract class OnSafeClickListener(
    private val interval: Int = 1000
) : View.OnClickListener {

    private var lastTimeClicked = 0L
    private val lock = Any()

    abstract fun onSafeClick(v: View)

    final override fun onClick(v: View?) {
        if (v == null) return
        synchronized(lock) {
            val current = SystemClock.elapsedRealtime()
            if (current - lastTimeClicked > interval) {
                lastTimeClicked = current
                onSafeClick(v)
            }
        }
    }
}

fun View.setOnSafeClickListener(interval: Int = 1000, delegate: (View) -> Unit) {
    setOnClickListener(object : OnSafeClickListener(interval) {
        override fun onSafeClick(v: View) {
            delegate(v)
        }
    })
}

fun View.setOnSafeClickListener(interval: Int = 1000, delegate: View.OnClickListener) {
    setOnClickListener(object : OnSafeClickListener(interval) {
        override fun onSafeClick(v: View) {
            delegate.onClick(v)
        }
    })
}

object ClickGuardBindingAdapter {

    @BindingAdapter("onSafeClick")
    @JvmStatic
    fun bindOnSafeClickListener(view: View, delegate: View.OnClickListener?) {
        when (delegate) {
            null -> {
                view.setOnClickListener(null)
            }
            is OnSafeClickListener -> {
                view.setOnClickListener(delegate)
            }
            else -> {
                view.setOnSafeClickListener(delegate = delegate)
            }
        }
    }
}
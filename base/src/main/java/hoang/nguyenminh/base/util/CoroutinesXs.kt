package hoang.nguyenminh.base.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectOnLifecycle(
    lifecycleOwner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    collector: FlowCollector<T>
): Job = lifecycleOwner.run {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            collect(collector)
        }
    }
}

fun <T> Flow<T>.collectLatestOnLifecycle(
    lifecycleOwner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    collector: suspend (T) -> Unit
): Job = lifecycleOwner.run {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            collectLatest(collector)
        }
    }
}
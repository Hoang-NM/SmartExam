package hoang.nguyenminh.base.util

import kotlinx.coroutines.flow.MutableStateFlow

class CountDelegation {

    private var count: Int = 0

    val flowOfCount = MutableStateFlow(0)

    fun increase() {
        count++
        flowOfCount.value = count
    }

    fun decrease() {
        if (count > 0) {
            count--
            flowOfCount.value = count
        }
    }

    fun clear() {
        count = 0
        flowOfCount.value = 0
    }
}
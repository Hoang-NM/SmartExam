package hoang.nguyenminh.smartexam.base

import android.app.Application
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.viewModelScope
import hoang.nguyenminh.base.scene.BaseActivity
import hoang.nguyenminh.base.scene.BaseAndroidViewModel
import hoang.nguyenminh.base.scene.BaseBottomSheetDialog
import hoang.nguyenminh.base.scene.BaseFragment
import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.BR
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.model.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class SmartExamActivity<T : ViewDataBinding> : BaseActivity<T>() {

    override fun getViewModelVariableId(): Int = BR.vm
}

abstract class SmartExamFragment<T : ViewDataBinding> : BaseFragment<T>() {

    override fun getViewModelVariableId(): Int = BR.vm
}

abstract class SmartExamBottomSheetDialog<T : ViewDataBinding> : BaseBottomSheetDialog<T>() {

    override fun getViewModelVariableId(): Int = BR.vm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_SmartExam_BottomSheetDialog)
    }
}

abstract class SmartExamViewModel(application: Application) : BaseAndroidViewModel(application) {

    protected inline fun <R, P> execute(
        useCase: CoroutinesUseCase<ResultWrapper<R>, P>,
        params: P,
        crossinline onSuccess: (R) -> Unit,
        crossinline onError: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = useCase(coroutineContext, params)) {
                is ResultWrapper.Success -> onSuccess.invoke(response.value)
                is ResultWrapper.Error -> onError.invoke(response.throwable)
                is ResultWrapper.ParserError -> onParserError()
                is ResultWrapper.NetworkError -> onNetworkError()
            }
        }
    }

    fun onParserError() {
        // Not yet implemented
    }

    fun onNetworkError() {
        Timber.e("Network error")
    }
}
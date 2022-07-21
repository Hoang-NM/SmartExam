package hoang.nguyenminh.smartexam.base

import android.app.Application
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.viewModelScope
import hoang.nguyenminh.base.scene.BaseActivity
import hoang.nguyenminh.base.scene.BaseAndroidViewModel
import hoang.nguyenminh.base.scene.BaseBottomSheetDialog
import hoang.nguyenminh.base.scene.BaseFragment
import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.base.util.ConfirmRequest
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.BR
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.databinding.DialogConfirmBinding
import hoang.nguyenminh.smartexam.model.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import hoang.nguyenminh.base.R as baseR

abstract class SmartExamActivity<T : ViewDataBinding> : BaseActivity<T>() {

    override fun getViewModelVariableId(): Int = BR.vm

    override fun provideConfirmDialog(confirmRequest: ConfirmRequest): DialogFragment =
        SmartExamConfirmDialog.newInstance(confirmRequest)
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

class SmartExamConfirmDialog : DialogFragment() {

    val KEY_CONFIRM_DIALOG = "KEY_CONFIRM_DIALOG"

    private var confirmRequest: ConfirmRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        confirmRequest ?: run {
            confirmRequest = arguments?.getSerializable(KEY_CONFIRM_DIALOG) as ConfirmRequest?
        }
        isCancelable = confirmRequest?.cancelable ?: true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCancelable(confirmRequest?.cancelable ?: true)
            setCanceledOnTouchOutside(confirmRequest?.cancelable ?: true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = DialogConfirmBinding.inflate(inflater, container, false).apply {
        request = confirmRequest
        btnPositive.setOnSafeClickListener {
            dismissAllowingStateLoss()
            confirmRequest?.onPositiveSelected?.invoke()
        }
        btnNegative.setOnSafeClickListener {
            dismissAllowingStateLoss()
            confirmRequest?.onNegativeSelected?.invoke()
        }
    }.root

    companion object {
        fun newInstance(request: ConfirmRequest): SmartExamConfirmDialog =
            SmartExamConfirmDialog().apply {
                confirmRequest = request
                arguments = bundleOf(KEY_CONFIRM_DIALOG to request)
            }
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

    protected fun notifyError(
        message: String,
        cancelable: Boolean = false,
        onConfirmBack: () -> Unit = {},
    ) {
        requestConfirm(
            ConfirmRequest(
                message = message,
                positive = resource.getString(baseR.string.close),
                cancelable = cancelable,
                onPositiveSelected = onConfirmBack,
            )
        )
    }

    protected fun notifyError(
        @StringRes message: Int,
        cancelable: Boolean = false,
        onConfirmBack: () -> Unit = {},
    ) {
        requestConfirm(
            ConfirmRequest(
                message = resource.getString(message),
                positive = resource.getString(baseR.string.close),
                cancelable = cancelable,
                onPositiveSelected = onConfirmBack,
            )
        )
    }

    protected fun notifyMessage(
        message: String,
        cancelable: Boolean = false,
        onConfirm: () -> Unit = {},
    ) {
        requestConfirm(
            ConfirmRequest(
                message = message,
                positive = resource.getString(baseR.string.common_ok),
                cancelable = cancelable,
                onPositiveSelected = onConfirm,
            )
        )
    }

    protected fun notifyMessage(
        @StringRes message: Int,
        cancelable: Boolean = false,
        onConfirm: () -> Unit = {},
    ) {
        requestConfirm(
            ConfirmRequest(
                message = resource.getString(message),
                positive = resource.getString(baseR.string.common_ok),
                cancelable = cancelable,
                onPositiveSelected = onConfirm,
            )
        )
    }

    fun notifyConfirmation(
        message: String,
        cancelable: Boolean = false,
        onConfirm: () -> Unit,
        onCancel: () -> Unit
    ) {
        requestConfirm(
            ConfirmRequest(
                message = message,
                positive = resource.getString(baseR.string.confirm),
                negative = resource.getString(baseR.string.cancel),
                cancelable = cancelable,
                onPositiveSelected = onConfirm,
                onNegativeSelected = onCancel
            )
        )
    }

    fun notifyConfirmation(
        @StringRes message: Int,
        cancelable: Boolean = false,
        onConfirm: () -> Unit,
        onCancel: () -> Unit
    ) {
        requestConfirm(
            ConfirmRequest(
                message = resource.getString(message),
                positive = resource.getString(baseR.string.confirm),
                negative = resource.getString(baseR.string.cancel),
                cancelable = cancelable,
                onPositiveSelected = onConfirm,
                onNegativeSelected = onCancel
            )
        )
    }
}
package hoang.nguyenminh.base.scene

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import hoang.nguyenminh.base.util.ConfirmRequest
import hoang.nguyenminh.base.util.buildAlertDialog
import hoang.nguyenminh.base.util.collectOnLifecycle
import java.lang.ref.WeakReference

abstract class BaseDialog<T : ViewDataBinding> : DialogFragment(), Scene {

    protected var binding: T? = null

    protected abstract val viewModel: BaseAndroidViewModel

    private var baseActivityWeakReference: WeakReference<BaseActivity<*>?>? = null

    protected fun getBaseActivity(): BaseActivity<*>? {
        return baseActivityWeakReference?.get() ?: (activity as? BaseActivity<*>)?.also {
            baseActivityWeakReference = WeakReference(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.run {
            onBind(arguments)
            onAttachScene(this@BaseDialog)
            onViewModelCreated()
            onReady()
        }
    }

    protected fun onViewModelCreated() {
        viewModel.getConfirmEvent().collectOnLifecycle(this) {
            showMessage(it)
        }
        viewModel.getToastEvent().collectOnLifecycle(this) {
            toast(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = onCreateViewDataBinding(inflater, container).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
        setVariable(getViewModelVariableId(), viewModel)
        executePendingBindings()
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.run {
            onDetachScene(this@BaseDialog)
            onUnBind()
        }
    }

    protected abstract fun getViewModelVariableId(): Int

    protected abstract fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): T

    override fun getSceneResource(): Resources = resources

    override fun lifecycleOwner(): LifecycleOwner = this

    override fun showMessage(request: ConfirmRequest) {
        getBaseActivity()?.showMessage(request)
    }

    override fun toast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

class ConfirmDialog(private val confirmRequest: ConfirmRequest) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        confirmRequest.buildAlertDialog(requireContext())
}

fun FragmentActivity.dismissDialogFragmentByTag(tag: String?) {
    supportFragmentManager.findFragmentByTag(tag)?.run {
        when (this) {
            is DialogFragment -> if (isAdded) dismissAllowingStateLoss()
        }
    }
}
package hoang.nguyenminh.base.scene

import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import hoang.nguyenminh.base.util.ConfirmRequest
import hoang.nguyenminh.base.util.CountDelegation
import hoang.nguyenminh.base.util.collectOnLifecycle
import hoang.nguyenminh.base.util.weakLazy

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), Scene {

    companion object {
        private const val TAG_LOADING_DIALOG = "TAG_LOADING_DIALOG"
        private const val TAG_CONFIRM_DIALOG = "TAG_CONFIRM_DIALOG"
    }

    private val taskCount = CountDelegation()

    private val loadingDialogWeakRef by weakLazy {
        provideLoadingDialog()
    }

    protected var binding: T? = null

    protected abstract val viewModel: BaseAndroidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.run {
            onBind(intent.extras)
            onAttachScene(this@BaseActivity)
            onViewModelCreated()
            onReady()
        }
        binding = onCreateViewDataBinding().apply {
            lifecycleOwner = this@BaseActivity
            setVariable(getViewModelVariableId(), this)
            executePendingBindings()
        }
    }

    private fun onViewModelCreated() {
        viewModel.getTaskCount().collectOnLifecycle(this) {
            if (it == 0) dismissLoading()
            else showLoading()
        }
        viewModel.getConfirmEvent().collectOnLifecycle(this) {
            showMessage(it)
        }
        viewModel.getToastEvent().collectOnLifecycle(this) {
            toast(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        viewModel.run {
            onDetachScene(this@BaseActivity)
            onUnBind()
        }
    }

    protected abstract fun getViewModelVariableId(): Int

    protected open fun provideLoadingDialog(): DialogFragment = DialogFragment()

    protected open fun provideConfirmDialog(confirmRequest: ConfirmRequest): DialogFragment =
        ConfirmDialog(confirmRequest)

    protected abstract fun onCreateViewDataBinding(): T

    override fun getSceneResource(): Resources = resources

    override fun lifecycleOwner(): LifecycleOwner = this

    open fun showLoading() {
        loadingDialogWeakRef.run {
            show(supportFragmentManager, TAG_LOADING_DIALOG)
        }
    }

    open fun dismissLoading() {
        dismissDialogFragmentByTag(TAG_LOADING_DIALOG)
    }

    override fun showMessage(request: ConfirmRequest) {
        dismissDialogFragmentByTag(TAG_CONFIRM_DIALOG)
        provideConfirmDialog(request).show(supportFragmentManager, TAG_CONFIRM_DIALOG)
    }

    override fun toast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
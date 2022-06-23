package hoang.nguyenminh.base.scene

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(), Scene {

    protected var binding: T? = null

    protected abstract val viewModel: BaseAndroidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.run {
            onBind(savedInstanceState)
            onAttachScene(this@BaseActivity)
            onReady()
        }
        binding = onCreateViewDataBinding().apply {
            lifecycleOwner = this@BaseActivity
            setVariable(getViewModelVariableId(), this)
            executePendingBindings()
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

    protected abstract fun onCreateViewDataBinding(): T

    override fun getSceneResource(): Resources = resources

    override fun lifecycleOwner(): LifecycleOwner = this
}
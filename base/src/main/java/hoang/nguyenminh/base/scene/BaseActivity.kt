package hoang.nguyenminh.base.scene

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

abstract class BaseActivity : AppCompatActivity(), Scene {

    protected abstract val viewModel: BaseAndroidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = onCreateViewDataBinding().apply {
            lifecycleOwner = this@BaseActivity
        }
        viewModel.run {
            onBind(savedInstanceState)
            onAttachScene(this@BaseActivity)
            viewBinding.setVariable(getViewModelVariableId(), this)
            onReady()
        }
        viewBinding.executePendingBindings()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.run {
            onDetachScene(this@BaseActivity)
            onUnBind()
        }
    }

    protected abstract fun getViewModelVariableId(): Int

    protected abstract fun onCreateViewDataBinding(): ViewDataBinding

    override fun getSceneResource(): Resources = resources

    override fun lifecycleOwner(): LifecycleOwner = this
}
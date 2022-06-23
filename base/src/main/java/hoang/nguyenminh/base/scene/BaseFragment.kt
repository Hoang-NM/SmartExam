package hoang.nguyenminh.base.scene

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

abstract class BaseFragment<T : ViewDataBinding> : Fragment(), Scene {

    protected var binding: T? = null

    protected abstract val viewModel: BaseAndroidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.run {
            onBind(savedInstanceState)
            onAttachScene(this@BaseFragment)
            onReady()
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
            onDetachScene(this@BaseFragment)
            onUnBind()
        }
    }

    protected abstract fun getViewModelVariableId(): Int

    protected abstract fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): T

    override fun getSceneResource(): Resources = resources

    override fun lifecycleOwner(): LifecycleOwner = this
}
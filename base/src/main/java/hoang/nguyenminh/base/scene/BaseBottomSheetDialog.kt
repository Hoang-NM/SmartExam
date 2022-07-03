package hoang.nguyenminh.base.scene

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialog<T : ViewDataBinding> : BottomSheetDialogFragment(), Scene {

    protected var binding: T? = null

    protected abstract val viewModel: BaseAndroidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.run {
            onBind(arguments)
            onAttachScene(this@BaseBottomSheetDialog)
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
            onDetachScene(this@BaseBottomSheetDialog)
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
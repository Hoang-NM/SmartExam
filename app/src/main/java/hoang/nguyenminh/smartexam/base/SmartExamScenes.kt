package hoang.nguyenminh.smartexam.base

import android.app.Application
import androidx.databinding.ViewDataBinding
import hoang.nguyenminh.base.scene.BaseActivity
import hoang.nguyenminh.base.scene.BaseAndroidViewModel
import hoang.nguyenminh.base.scene.BaseFragment
import hoang.nguyenminh.smartexam.BR

abstract class SmartExamActivity<T : ViewDataBinding> : BaseActivity<T>() {

    abstract override val viewModel: SmartExamViewModel

    override fun getViewModelVariableId(): Int = BR.vm
}

abstract class SmartExamFragment<T : ViewDataBinding> : BaseFragment<T>() {

    abstract override val viewModel: SmartExamViewModel

    override fun getViewModelVariableId(): Int = BR.vm
}

abstract class SmartExamViewModel(application: Application) : BaseAndroidViewModel(application)
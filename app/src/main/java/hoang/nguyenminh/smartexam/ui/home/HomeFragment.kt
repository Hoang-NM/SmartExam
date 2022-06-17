package hoang.nguyenminh.smartexam.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.scene.BaseFragment
import hoang.nguyenminh.smartexam.BR
import hoang.nguyenminh.smartexam.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    override val viewModel by viewModels<HomeViewModel>()

    override fun getViewModelVariableId(): Int = BR.vm

    private var binding: FragmentHomeBinding? = null

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): ViewDataBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
        binding = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
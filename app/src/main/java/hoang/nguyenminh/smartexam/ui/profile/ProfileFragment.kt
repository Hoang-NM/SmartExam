package hoang.nguyenminh.smartexam.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.scene.BaseFragment
import hoang.nguyenminh.smartexam.BR
import hoang.nguyenminh.smartexam.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    override val viewModel by viewModels<ProfileViewModel>()

    override fun getViewModelVariableId(): Int = BR.vm

    private var binding: FragmentProfileBinding? = null

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): ViewDataBinding = FragmentProfileBinding.inflate(inflater, container, false).apply {
        binding = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
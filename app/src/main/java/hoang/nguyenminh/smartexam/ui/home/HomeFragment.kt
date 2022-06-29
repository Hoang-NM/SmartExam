package hoang.nguyenminh.smartexam.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : SmartExamFragment<FragmentHomeBinding>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
        binding = this
    }
}
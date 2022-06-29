package hoang.nguyenminh.smartexam.ui.exam.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamDetailBinding

@AndroidEntryPoint
class ExamDetailFragment : SmartExamFragment<FragmentExamDetailBinding>() {

    override val viewModel: ExamDetailViewModel by viewModels()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentExamDetailBinding =
        FragmentExamDetailBinding.inflate(inflater, container, false).apply {
            binding = this
        }
}
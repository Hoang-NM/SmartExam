package hoang.nguyenminh.smartexam.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : SmartExamFragment<FragmentHomeBinding>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
        viewModel.flowOfHomeContent.collectLatestOnLifecycle(viewLifecycleOwner) {
            viewTotalCount.label.text = getString(R.string.format_total_exam_count, it?.totalExams)
            viewCompletedCount.label.text =
                getString(R.string.format_completed_exam_count, it?.completedExams)
            viewTodoCount.label.text = getString(R.string.format_todo_exam_count, it?.todoExams?.size)
        }
    }
}
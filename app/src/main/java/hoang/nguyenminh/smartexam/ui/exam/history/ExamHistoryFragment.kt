package hoang.nguyenminh.smartexam.ui.exam.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamHistoryBinding
import hoang.nguyenminh.smartexam.ui.exam.history.adapter.ExamHistoryAdapter

@AndroidEntryPoint
class ExamHistoryFragment : SmartExamFragment<FragmentExamHistoryBinding>() {

    override val viewModel by viewModels<ExamHistoryViewModel>()

    private var adapter: ExamHistoryAdapter? = null

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentExamHistoryBinding =
        FragmentExamHistoryBinding.inflate(inflater, container, false).apply {
            binding = this
            recHistory.adapter = ExamHistoryAdapter { id ->
                findNavController().navigate(NavigationMainDirections.toExamDetail(id))
            }.also {
                adapter = it
            }
            viewModel.flowOfHistories.collectLatestOnLifecycle(viewLifecycleOwner) {
                adapter?.submitList(it)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}
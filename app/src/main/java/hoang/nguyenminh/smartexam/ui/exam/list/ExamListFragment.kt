package hoang.nguyenminh.smartexam.ui.exam.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamListBinding
import hoang.nguyenminh.smartexam.model.exam.ExamAction
import hoang.nguyenminh.smartexam.ui.exam.list.adapter.ExamListAdapter

@AndroidEntryPoint
class ExamListFragment : SmartExamFragment<FragmentExamListBinding>() {

    override val viewModel: ExamListViewModel by viewModels()

    private val args by navArgs<ExamListFragmentArgs>()

    private var adapter: ExamListAdapter? = null

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentExamListBinding =
        FragmentExamListBinding.inflate(inflater, container, false).apply {
            requireActivity().title = when (args.action) {
                ExamAction.EXECUTION -> getString(R.string.lbl_exam_list)
                ExamAction.VIEW_RESULT -> getString(R.string.lbl_exam_history)
                else -> ""
            }
            recHistory.adapter = ExamListAdapter {
                findNavController().navigate(NavigationMainDirections.toExamDetail(it, args.action))
            }.also {
                adapter = it
            }
            viewModel.flowOfExamList.collectLatestOnLifecycle(viewLifecycleOwner) {
                adapter?.submitList(it)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}
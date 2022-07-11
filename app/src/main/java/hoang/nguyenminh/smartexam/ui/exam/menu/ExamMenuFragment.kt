package hoang.nguyenminh.smartexam.ui.exam.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamBinding
import hoang.nguyenminh.smartexam.model.AppNavigator
import hoang.nguyenminh.smartexam.ui.exam.menu.adapter.ExamMenuAdapter

@AndroidEntryPoint
class ExamFragment : SmartExamFragment<FragmentExamBinding>() {

    override val viewModel: ExamMenuViewModel by viewModels()

    private var adapter: ExamMenuAdapter? = null

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentExamBinding = FragmentExamBinding.inflate(inflater, container, false).apply {
        binding = this
        recMenu.adapter = ExamMenuAdapter { _, model ->
            when (model.id) {
                AppNavigator.MENU_EXAM_EXECUTION -> findNavController().navigate(
                    NavigationMainDirections.toExamList(ExamAction.EXECUTION)
                )
                AppNavigator.MENU_EXAM_CAPTURE -> findNavController().navigate(
                    NavigationMainDirections.toPhotoOption()
                )
                AppNavigator.MENU_EXAM_HISTORY -> findNavController().navigate(
                    NavigationMainDirections.toExamList(ExamAction.VIEW_RESULT)
                )
                else -> throw IllegalArgumentException("Exam menu item not found")
            }
        }.also {
            adapter = it
        }

        viewModel.flowOfMenuItem.collectLatestOnLifecycle(viewLifecycleOwner) {
            it ?: return@collectLatestOnLifecycle
            adapter?.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}

object ExamAction {
    const val EXECUTION = 0
    const val VIEW_RESULT = 1
}
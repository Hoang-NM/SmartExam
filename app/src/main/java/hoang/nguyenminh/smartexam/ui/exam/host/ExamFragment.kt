package hoang.nguyenminh.smartexam.ui.exam.host

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.scene.BaseFragment
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.smartexam.BR
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.databinding.FragmentExamBinding
import hoang.nguyenminh.smartexam.model.AppNavigator
import hoang.nguyenminh.smartexam.ui.exam.host.adapter.ExamMenuAdapter

@AndroidEntryPoint
class ExamFragment : BaseFragment<FragmentExamBinding>() {

    override val viewModel by viewModels<ExamViewModel>()

    override fun getViewModelVariableId(): Int = BR.vm

    private var adapter: ExamMenuAdapter? = null

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentExamBinding = FragmentExamBinding.inflate(inflater, container, false).apply {
        binding = this
        recMenu.adapter = ExamMenuAdapter { _, model ->
            when (model.id) {
                AppNavigator.MENU_EXAM_EXECUTION -> findNavController().navigate(
                    NavigationMainDirections.toExamExecution(50)
                )
                AppNavigator.MENU_EXAM_CAPTURE -> findNavController().navigate(
                    NavigationMainDirections.toExamCapture()
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
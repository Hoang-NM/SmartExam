package hoang.nguyenminh.smartexam.ui.exam.host

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.databinding.FragmentExamBinding
import hoang.nguyenminh.smartexam.model.AppNavigator
import hoang.nguyenminh.smartexam.ui.exam.host.adapter.ExamMenuAdapter
import hoang.nguyenminh.smartexam.util.collectLatestOnLifecycle

@AndroidEntryPoint
class ExamFragment : Fragment() {

    private var binding: FragmentExamBinding? = null

    private val viewModel by viewModels<ExamViewModel>()

    private var adapter: ExamMenuAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentExamBinding.inflate(inflater, container, false).apply {
        binding = this
        recMenu.adapter = ExamMenuAdapter { _, model ->
            when (model.id) {
                AppNavigator.MENU_EXAM_EXECUTION -> findNavController().navigate(
                    NavigationMainDirections.toExamExecution()
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
            adapter?.notifyItemRangeInserted(0, it.size)
        }
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        adapter = null
    }
}
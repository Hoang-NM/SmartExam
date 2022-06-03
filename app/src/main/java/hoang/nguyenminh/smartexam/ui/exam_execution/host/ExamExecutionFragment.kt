package hoang.nguyenminh.smartexam.ui.exam_execution.host

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.databinding.FragmentExamExecutionBinding
import hoang.nguyenminh.smartexam.ui.exam_execution.host.adapter.ExamExecutionPagerAdapter
import hoang.nguyenminh.smartexam.util.collectLatestOnLifecycle

@AndroidEntryPoint
class ExamExecutionFragment : Fragment() {

    private val viewModel by viewModels<ExamExecutionViewModel>()

    private var binding: FragmentExamExecutionBinding? = null

    private var adapter: ExamExecutionPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentExamExecutionBinding.inflate(inflater, container, false).apply {
        binding = this
        vpQuestion.adapter = ExamExecutionPagerAdapter(this@ExamExecutionFragment).also {
            adapter = it
        }
        viewModel.flowOfExam.collectLatestOnLifecycle(viewLifecycleOwner) {
            it ?: return@collectLatestOnLifecycle
            adapter?.listOfQuestions = it.questions
        }
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}
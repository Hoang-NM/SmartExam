package hoang.nguyenminh.smartexam.ui.exam.submit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.base.util.createConfirmDialog
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamSubmitBinding
import hoang.nguyenminh.smartexam.model.exam.QuestionModel
import hoang.nguyenminh.smartexam.ui.exam.submit.adapter.QuestionAnswerAdapter
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class ExamSubmitFragment : SmartExamFragment<FragmentExamSubmitBinding>() {

    override val viewModel: ExamSubmitViewModel by viewModels()

    private var adapter: QuestionAnswerAdapter? = null

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentExamSubmitBinding =
        FragmentExamSubmitBinding.inflate(inflater, container, false).apply {
            recQuestions.adapter = QuestionAnswerAdapter().also {
                adapter = it
            }
            viewModel.flowOfDetail.collectLatestOnLifecycle(viewLifecycleOwner) {
                it ?: return@collectLatestOnLifecycle
                lblName.text = it.name
                it.questions.map(QuestionModel::toAnswerModel).let { adapter?.submitList(it) }
            }
            btnSubmit.setOnSafeClickListener {
                createConfirmDialog(message = getString(R.string.message_confirm_submit_exam),
                    onPositiveSelected = {
                        submitExam()
                    }
                )
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    private fun submitExam() = runBlocking {
        viewModel.submitExam().join()
        findNavController().navigate(NavigationMainDirections.popToExamMenu())
    }
}
package hoang.nguyenminh.smartexam.ui.exam.execution.question

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamQuestionBinding
import hoang.nguyenminh.smartexam.model.exam.Question
import timber.log.Timber

@AndroidEntryPoint
class ExamQuestionFragment : SmartExamFragment<FragmentExamQuestionBinding>() {

    private val KEY_QUESTION = "question"

    override val viewModel: ExamQuestionViewModel by viewModels()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentExamQuestionBinding =
        FragmentExamQuestionBinding.inflate(inflater, container, false).apply {
            binding = this
            val question: Question = arguments?.getParcelable(KEY_QUESTION) ?: return@apply
            lblQuestion.text = question.question
            lblOptionA.text = question.optionA
            lblOptionB.text = question.optionB
            lblOptionC.text = question.optionC
            lblOptionD.text = question.optionD
        }

    companion object {
        fun newInstance(question: Question): ExamQuestionFragment = ExamQuestionFragment().apply {
            arguments = bundleOf(KEY_QUESTION to question)
        }
    }

    private fun saveSelectedChoice(answer: String) {
        Timber.e("Save answer: answer = $answer")
    }
}
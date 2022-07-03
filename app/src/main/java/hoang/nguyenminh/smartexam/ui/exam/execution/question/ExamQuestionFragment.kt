package hoang.nguyenminh.smartexam.ui.exam.execution.question

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.BindingAdapters.viewCompatSelected
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamQuestionBinding
import hoang.nguyenminh.smartexam.model.exam.Question

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
            lblOptionA.setupOption(question.optionA, question.isASelected) {
                question.isASelected = !question.isASelected
                viewModel.saveSelectedChoice(question.id, "A")
            }
            lblOptionB.setupOption(question.optionB, question.isBSelected) {
                question.isBSelected = !question.isBSelected
                viewModel.saveSelectedChoice(question.id, "B")
            }
            lblOptionC.setupOption(question.optionC, question.isCSelected) {
                question.isCSelected = !question.isCSelected
                viewModel.saveSelectedChoice(question.id, "C")
            }
            lblOptionD.setupOption(question.optionD, question.isDSelected) {
                question.isDSelected = !question.isDSelected
                viewModel.saveSelectedChoice(question.id, "D")
            }
        }

    companion object {
        fun newInstance(question: Question): ExamQuestionFragment = ExamQuestionFragment().apply {
            arguments = bundleOf(KEY_QUESTION to question)
        }
    }
}

fun TextView.setupOption(content: String, selectedState: Boolean, onClick: () -> Unit) {
    text = content
    viewCompatSelected(selectedState)
    setOnClickListener {
        isSelected = !isSelected
        onClick()
    }
}
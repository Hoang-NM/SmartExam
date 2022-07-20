package hoang.nguyenminh.smartexam.ui.exam.execution.question

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamQuestionBinding
import hoang.nguyenminh.smartexam.model.exam.QuestionModel
import hoang.nguyenminh.smartexam.ui.exam.execution.question.adapter.QuestionChoiceAdapter

@AndroidEntryPoint
class ExamQuestionFragment : SmartExamFragment<FragmentExamQuestionBinding>() {

    private val KEY_INDEX = "index"
    private val KEY_QUESTION = "question"

    override val viewModel: ExamQuestionViewModel by viewModels()

    private var adapter: QuestionChoiceAdapter? = null

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentExamQuestionBinding =
        FragmentExamQuestionBinding.inflate(inflater, container, false).apply {
            val index: Int = arguments?.getInt(KEY_INDEX) ?: return@apply
            val question: QuestionModel = arguments?.getParcelable(KEY_QUESTION) ?: return@apply
            lblQuestion.text =
                getString(R.string.format_question_content, index, question.content)
            recChoices.adapter = QuestionChoiceAdapter { pos, model ->
                saveSelectedChoice(pos, question)
            }.also {
                adapter = it
            }
            adapter?.submitList(question.choices)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    companion object {
        fun newInstance(index: Int, question: QuestionModel): ExamQuestionFragment =
            ExamQuestionFragment().apply {
                arguments = bundleOf(KEY_QUESTION to question)
            }
    }

    private fun saveSelectedChoice(position: Int, question: QuestionModel) {
        viewModel.saveSelectedChoice(question)
        adapter?.notifyItemChanged(position)
    }
}
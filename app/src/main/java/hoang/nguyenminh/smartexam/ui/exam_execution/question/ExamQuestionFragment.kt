package hoang.nguyenminh.smartexam.ui.exam_execution.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.databinding.FragmentExamQuestionBinding
import hoang.nguyenminh.smartexam.model.exam.Choice
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.ui.exam_execution.question.adapter.QuestionChoiceAdapter
import timber.log.Timber

@AndroidEntryPoint
class ExamQuestionFragment : Fragment() {

    private val KEY_QUESTION = "question"

    private val viewModel by viewModels<ExamQuestionViewModel>()

    private var binding: FragmentExamQuestionBinding? = null

    private var adapter: QuestionChoiceAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentExamQuestionBinding.inflate(inflater, container, false).apply {
        binding = this
        val question: Question = arguments?.getParcelable(KEY_QUESTION) ?: return@apply
        lblQuestion.text = question.question
        recChoices.adapter = QuestionChoiceAdapter { pos, model ->
            saveSelectedChoice(pos, model)
        }.also {
            adapter = it
        }
        adapter?.submitList(question.choices)
    }.root

    companion object {
        fun newInstance(question: Question): ExamQuestionFragment = ExamQuestionFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_QUESTION, question)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    private fun saveSelectedChoice(position: Int, choice: Choice) {
        Timber.e("Save choice: index = ${choice.index} - content = ${choice.content}")
        adapter?.notifyItemChanged(position)
    }
}
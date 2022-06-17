package hoang.nguyenminh.smartexam.ui.exam.execution.question

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.scene.BaseFragment
import hoang.nguyenminh.smartexam.BR
import hoang.nguyenminh.smartexam.databinding.FragmentExamQuestionBinding
import hoang.nguyenminh.smartexam.model.exam.Choice
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.ui.exam.execution.question.adapter.QuestionChoiceAdapter
import timber.log.Timber

@AndroidEntryPoint
class ExamQuestionFragment : BaseFragment() {

    private val KEY_QUESTION = "question"

    override val viewModel by viewModels<ExamQuestionViewModel>()

    override fun getViewModelVariableId(): Int = BR.vm

    private var binding: FragmentExamQuestionBinding? = null

    private var adapter: QuestionChoiceAdapter? = null

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): ViewDataBinding = FragmentExamQuestionBinding.inflate(inflater, container, false).apply {
        binding = this
        val question: Question = arguments?.getParcelable(KEY_QUESTION) ?: return@apply
        lblQuestion.text = question.question
        recChoices.adapter = QuestionChoiceAdapter { pos, model ->
            saveSelectedChoice(pos, model)
        }.also {
            adapter = it
        }
        adapter?.submitList(question.choices)
    }

    companion object {
        fun newInstance(question: Question): ExamQuestionFragment = ExamQuestionFragment().apply {
            arguments = bundleOf(KEY_QUESTION to question)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        adapter = null
    }

    private fun saveSelectedChoice(position: Int, choice: Choice) {
        Timber.e("Save choice: index = ${choice.index} - content = ${choice.content}")
        adapter?.notifyItemChanged(position)
    }
}
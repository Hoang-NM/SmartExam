package hoang.nguyenminh.smartexam.ui.exam.execution.navigation_dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.base.util.setupPercentage
import hoang.nguyenminh.smartexam.base.SmartExamBottomSheetDialog
import hoang.nguyenminh.smartexam.databinding.DialogQuestionNavigationBinding
import hoang.nguyenminh.smartexam.ui.exam.execution.host.KEY_QUESTION_INDEX
import hoang.nguyenminh.smartexam.ui.exam.execution.navigation_dialog.adapter.ExamQuestionIndexAdapter

@AndroidEntryPoint
class ExamQuestionNavigationDialog : SmartExamBottomSheetDialog<DialogQuestionNavigationBinding>() {

    override val viewModel: ExamQuestionNavigationViewModel by viewModels()

    private var adapter: ExamQuestionIndexAdapter? = null

    override fun onStart() {
        super.onStart()
        setupPercentage(0.6f)
    }

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogQuestionNavigationBinding =
        DialogQuestionNavigationBinding.inflate(inflater, container, false).apply {
            binding = this
            recQuestions.adapter = ExamQuestionIndexAdapter { question ->
                activity?.onBackPressed()
                setFragmentResult(KEY_QUESTION_INDEX, bundleOf(KEY_QUESTION_INDEX to question.index))
            }.apply {
                adapter = this
            }

            viewModel.flowOfQuestionIndexes.collectLatestOnLifecycle(lifecycleOwner()) {
                adapter?.submitList(it)
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}
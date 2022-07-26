package hoang.nguyenminh.smartexam.ui.exam.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.BindingAdapters.viewCompatVisibility
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamDetailBinding
import hoang.nguyenminh.smartexam.model.exam.ExamAction
import hoang.nguyenminh.smartexam.model.exam.ExamStatus
import hoang.nguyenminh.smartexam.model.exam.QuestionModel
import hoang.nguyenminh.smartexam.ui.exam.submit.adapter.QuestionAnswerAdapter

@AndroidEntryPoint
class ExamDetailFragment : SmartExamFragment<FragmentExamDetailBinding>() {

    override val viewModel: ExamDetailViewModel by viewModels()

    private val args by navArgs<ExamDetailFragmentArgs>()

    private var adapter: QuestionAnswerAdapter? = null

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentExamDetailBinding =
        FragmentExamDetailBinding.inflate(inflater, container, false).apply {
            lblResult.viewCompatVisibility(args.action == ExamAction.VIEW_RESULT)
            recAnswers.viewCompatVisibility(args.action == ExamAction.VIEW_RESULT)

            btnCaptureExam.viewCompatVisibility(args.action == ExamAction.EXECUTION)
            btnCaptureExam.setOnSafeClickListener {
                findNavController().navigate(NavigationMainDirections.toPhotoOption(args.exam.id))
            }

            btnEnterExam.viewCompatVisibility(args.action == ExamAction.EXECUTION)
            btnEnterExam.setOnSafeClickListener {
                findNavController().navigate(
                    NavigationMainDirections.toExamExecution(args.exam.id, ExamStatus.INITIALIZE)
                )
            }

            recAnswers.adapter = QuestionAnswerAdapter().also {
                adapter = it
            }
            viewModel.flowOfDetail.collectLatestOnLifecycle(lifecycleOwner()) {
                it ?: return@collectLatestOnLifecycle
                it.questions.map(QuestionModel::toAnswerModel).let { adapter?.submitList(it) }
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }
}
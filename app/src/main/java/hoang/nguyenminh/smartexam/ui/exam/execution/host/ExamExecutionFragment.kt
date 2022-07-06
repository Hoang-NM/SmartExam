package hoang.nguyenminh.smartexam.ui.exam.execution.host

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.BindingAdapters.viewCompatVisibility
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamExecutionBinding
import hoang.nguyenminh.smartexam.ui.exam.execution.host.adapter.ExamExecutionPagerAdapter

@AndroidEntryPoint
class ExamExecutionFragment : SmartExamFragment<FragmentExamExecutionBinding>() {

    override val viewModel: ExamExecutionViewModel by viewModels()

    private var pagerAdapter: ExamExecutionPagerAdapter? = null

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentExamExecutionBinding =
        FragmentExamExecutionBinding.inflate(inflater, container, false).apply {
            binding = this
            vpQuestion.apply {
                adapter = ExamExecutionPagerAdapter(this@ExamExecutionFragment).also {
                    pagerAdapter = it
                }
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    @SuppressLint("SetTextI18n")
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        pagerAdapter?.isLastPage(position)
                            ?.let { btnFinish.viewCompatVisibility(it) }
                        viewBottom.lblQuestionInfo.text =
                            "${position + 1} / ${pagerAdapter?.itemCount}"
                    }
                })
            }

            viewBottom.apply {
                imgBack.setOnClickListener {
                    vpQuestion.setCurrentItem(vpQuestion.currentItem - 1, true)
                }
                imgNext.setOnClickListener {
                    vpQuestion.setCurrentItem(vpQuestion.currentItem + 1, true)
                }
                lblQuestionInfo.setOnSafeClickListener {
                    findNavController().navigate(
                        NavigationMainDirections.toQuestionNavigation(
                            pagerAdapter?.listOfQuestions?.size ?: 1,
                            vpQuestion.currentItem + 1
                        )
                    )
                }
            }

            btnFinish.setOnClickListener {
                viewModel.clearSavedExamProgress()
                Toast.makeText(requireContext(), "Finish exam", Toast.LENGTH_SHORT).show()
                activity?.onBackPressed()
            }

            viewModel.flowOfExam.collectLatestOnLifecycle(viewLifecycleOwner) {
                it ?: return@collectLatestOnLifecycle
                pagerAdapter?.listOfQuestions = it.questions
                vpQuestion.offscreenPageLimit = it.questions.size
            }

            setFragmentResultListener(KEY_QUESTION_INDEX) { key, bundle ->
                vpQuestion.currentItem = bundle.getInt(key) - 1
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        pagerAdapter = null
    }
}

const val KEY_QUESTION_INDEX = "KEY_QUESTION_INDEX"
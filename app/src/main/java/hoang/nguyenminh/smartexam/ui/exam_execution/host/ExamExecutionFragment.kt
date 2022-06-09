package hoang.nguyenminh.smartexam.ui.exam_execution.host

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.databinding.FragmentExamExecutionBinding
import hoang.nguyenminh.smartexam.ui.exam_execution.host.adapter.ExamExecutionPagerAdapter
import hoang.nguyenminh.smartexam.util.BindingAdapters.viewCompatVisibility
import hoang.nguyenminh.smartexam.util.collectLatestOnLifecycle

@AndroidEntryPoint
class ExamExecutionFragment : Fragment() {

    private val viewModel by viewModels<ExamExecutionViewModel>()

    private var binding: FragmentExamExecutionBinding? = null

    private var pagerAdapter: ExamExecutionPagerAdapter? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentExamExecutionBinding.inflate(inflater, container, false).apply {
        binding = this
        vpQuestion.apply {
            adapter = ExamExecutionPagerAdapter(this@ExamExecutionFragment).also {
                pagerAdapter = it
            }
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    pagerAdapter?.isLastPage(position)?.let { btnFinish.viewCompatVisibility(it) }
                    viewBottom.lblQuestionInfo.text = "${position + 1} / ${pagerAdapter?.itemCount}"
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
        }

        btnFinish.setOnClickListener {
            Toast.makeText(requireContext(), "Finish exam", Toast.LENGTH_SHORT).show()
        }

        viewModel.flowOfExam.collectLatestOnLifecycle(viewLifecycleOwner) {
            it ?: return@collectLatestOnLifecycle
            pagerAdapter?.listOfQuestions = it.questions
            vpQuestion.offscreenPageLimit = it.questions.size
        }
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        pagerAdapter = null
    }
}
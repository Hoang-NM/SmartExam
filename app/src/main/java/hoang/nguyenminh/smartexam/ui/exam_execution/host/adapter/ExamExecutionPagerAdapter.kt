package hoang.nguyenminh.smartexam.ui.exam_execution.host.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.ui.exam_execution.question.ExamQuestionFragment

class ExamExecutionPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var listOfQuestions = listOf<Question>()

    override fun getItemCount(): Int = listOfQuestions.size

    override fun createFragment(position: Int): Fragment =
        ExamQuestionFragment.newInstance(listOfQuestions[position])
}
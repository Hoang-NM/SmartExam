package hoang.nguyenminh.smartexam.ui.exam.execution.host.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import hoang.nguyenminh.smartexam.model.exam.QuestionModel
import hoang.nguyenminh.smartexam.ui.exam.execution.question.ExamQuestionFragment

class ExamExecutionPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var listOfQuestions = listOf<QuestionModel>()

    override fun getItemCount(): Int = listOfQuestions.size

    override fun createFragment(position: Int): Fragment =
        ExamQuestionFragment.newInstance(listOfQuestions[position])

    fun isLastPage(currentPagePosition: Int) = currentPagePosition == listOfQuestions.size - 1
}
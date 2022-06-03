package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.smartexam.model.exam.Choice
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import hoang.nguyenminh.smartexam.util.DateTimeXs
import javax.inject.Inject

class GetExamQuestionUseCase @Inject constructor(private val repository: SmartExamCloudRepository) {

    fun getExamQuestion(): Exam {
        val choices = listOf(
            Choice(0, "Choice 1"), Choice(1, "Choice 2"),
            Choice(2, "Choice 3"), Choice(3, "Choice 4")
        )
        val questions = listOf(
            Question(0, "Question 1", choices),
            Question(1, "Question 2", choices),
            Question(2, "Question 3", choices),
            Question(3, "Question 4", choices),
            Question(4, "Question 5", choices),
        )
        return Exam(0, 60 * DateTimeXs.MINUTE, questions)
    }
}
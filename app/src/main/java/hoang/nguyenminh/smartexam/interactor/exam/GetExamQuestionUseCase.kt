package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.base.util.DateTimeXs
import hoang.nguyenminh.smartexam.model.exam.Choice
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

class GetExamQuestionUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<Exam, Unit>() {

    override suspend fun run(params: Unit): Exam = getExamQuestion()

    private fun getExamQuestion(): Exam {
        val choices = (1..200).map {
            Choice(it, "Choice $it")
        }
        var startIndex = -4
        val questions = (1..50).map {
            startIndex += 4
            Question(it, "Question $it", choices.subList(startIndex, startIndex + 4))
        }
        return Exam(0, 60 * DateTimeXs.MINUTE, questions)
    }
}
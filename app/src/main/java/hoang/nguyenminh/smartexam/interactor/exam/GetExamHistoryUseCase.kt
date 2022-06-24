package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.model.exam.ExamHistory
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

class GetExamHistoryUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<List<ExamHistory>, Unit>() {

    override suspend fun run(params: Unit): List<ExamHistory> = getExamHistories()

    private fun getExamHistories(): List<ExamHistory> = examList
}

val examList = listOf(
    ExamHistory(0, "English Exam", "2022/06/21"),
    ExamHistory(1, "Math Exam", "2022/06/21"),
    ExamHistory(2, "Chemistry Exam", "2022/06/21"),
    ExamHistory(3, "Physics Exam", "2022/06/21"),
    ExamHistory(4, "History Exam", "2022/06/21")
)
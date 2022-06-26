package hoang.nguyenminh.smartexam.repository.cloud

import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.model.exam.Question

interface SmartExamCloudRepository {

    suspend fun getExam(id: Int): Exam

    suspend fun getExamList(): List<Exam>

    suspend fun getQuestionList(id: Int): List<Question>
}
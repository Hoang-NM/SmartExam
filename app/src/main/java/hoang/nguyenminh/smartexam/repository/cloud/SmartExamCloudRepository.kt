package hoang.nguyenminh.smartexam.repository.cloud

import hoang.nguyenminh.smartexam.model.exam.Exam

interface SmartExamCloudRepository {

    suspend fun getExam(id: Int): Exam
}
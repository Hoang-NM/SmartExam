package hoang.nguyenminh.smartexam.repository.cloud

import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.module.network.SmartExamCloudService
import javax.inject.Inject

class SmartExamCloudRepositoryImpl @Inject constructor(
    private val service: SmartExamCloudService
) : SmartExamCloudRepository {

    override suspend fun getExam(id: Int): Exam = service.getExam(id)
}
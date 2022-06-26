package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.model.exam.ExamDetail
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

class GetExamDetailUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<ExamDetail, Int>() {

    override suspend fun run(params: Int): ExamDetail = getDetail(params)

    private fun getDetail(id: Int): ExamDetail = examList.firstOrNull { it.id == id }?.let {
        ExamDetail(it.id, it.name, it.executedDate, "50/50")
    } ?: ExamDetail(0, "Sample Exam", "1970/01/01", "50/50")
}
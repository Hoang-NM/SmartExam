package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

class GetExamDetailUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<Exam, Int>() {

    override suspend fun run(params: Int): Exam = repository.getExam(params)
}
package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.model.ResultWrapper
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

class GetExamHistoryUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<ResultWrapper<List<Exam>>, Unit>() {

    override suspend fun run(params: Unit): ResultWrapper<List<Exam>> = repository.getExamList()
}
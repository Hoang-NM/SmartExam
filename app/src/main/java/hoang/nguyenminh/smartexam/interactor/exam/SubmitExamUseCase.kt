package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.model.ResultWrapper
import hoang.nguyenminh.smartexam.model.exam.SubmitExamRequest
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

class SubmitExamUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<ResultWrapper<Unit>, SubmitExamRequest>() {

    override suspend fun run(params: SubmitExamRequest) = repository.submitExam(params)
}
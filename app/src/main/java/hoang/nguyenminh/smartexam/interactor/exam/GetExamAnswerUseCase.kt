package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.model.exam.ExamAnswer
import hoang.nguyenminh.smartexam.model.exam.GetExamAnswerRequest
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

class GetExamAnswerUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<ExamAnswer, GetExamAnswerRequest>() {

    override suspend fun run(params: GetExamAnswerRequest): ExamAnswer =
        repository.getExamAnswer(params)
}
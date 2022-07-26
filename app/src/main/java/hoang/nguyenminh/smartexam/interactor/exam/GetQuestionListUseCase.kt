package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

class GetQuestionListUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<List<Question>, Int>() {

    override suspend fun run(params: Int): List<Question> =
        repository.getQuestionList(params)
}
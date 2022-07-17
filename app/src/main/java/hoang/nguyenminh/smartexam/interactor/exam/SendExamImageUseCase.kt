package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.model.exam.SubmitExamImageRequest
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject


class SendExamImageUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<Unit, SubmitExamImageRequest>() {

    override suspend fun run(params: SubmitExamImageRequest) {
        repository.sendExamImage(params)
    }
}
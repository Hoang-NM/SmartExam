package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import timber.log.Timber
import javax.inject.Inject

class SubmitExamUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<Unit, Unit>() {

    override suspend fun run(params: Unit) {
        Timber.d("Submit exam successfully")
    }
}
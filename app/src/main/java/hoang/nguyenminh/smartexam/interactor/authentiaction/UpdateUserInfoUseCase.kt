package hoang.nguyenminh.smartexam.interactor.authentiaction

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.model.ResultWrapper
import hoang.nguyenminh.smartexam.model.authentication.UpdateUserInfoRequest
import hoang.nguyenminh.smartexam.repository.SmartExamCloudRepository
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<ResultWrapper<Unit>, UpdateUserInfoRequest>() {

    override suspend fun run(params: UpdateUserInfoRequest) = repository.updateUserInfo(params)
}
package hoang.nguyenminh.smartexam.interactor.main

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.interactor.homeInfo
import hoang.nguyenminh.smartexam.model.ResultWrapper
import hoang.nguyenminh.smartexam.model.main.HomeInfo
import hoang.nguyenminh.smartexam.repository.SmartExamCloudRepository
import javax.inject.Inject

class GetHomeInfoUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<ResultWrapper<HomeInfo>, Int>() {

    override suspend fun run(params: Int): ResultWrapper<HomeInfo> =
        ResultWrapper.Success(homeInfo())
}
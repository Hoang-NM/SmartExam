package hoang.nguyenminh.smartexam.interactor.main

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.smartexam.model.main.HomeInfo
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

class GetHomeInfoUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<HomeInfo, Int>() {

    override suspend fun run(params: Int): HomeInfo = repository.getHomeInfo(params)
}
package hoang.nguyenminh.smartexam.repository.cloud

import hoang.nguyenminh.smartexam.module.network.SmartExamCloudService
import javax.inject.Inject

class SmartExamCloudRepositoryImpl @Inject constructor(
    private val service: SmartExamCloudService
) : SmartExamCloudRepository {
}
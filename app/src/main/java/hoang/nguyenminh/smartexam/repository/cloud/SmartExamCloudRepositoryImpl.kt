package hoang.nguyenminh.smartexam.repository.cloud

import hoang.nguyenminh.smartexam.model.BaseResponse
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.module.network.SmartExamCloudService
import okhttp3.MultipartBody
import javax.inject.Inject

class SmartExamCloudRepositoryImpl @Inject constructor(
    private val service: SmartExamCloudService
) : SmartExamCloudRepository {

    private fun <T> BaseResponse<T>.unwrap(): T = data

    override suspend fun getExam(id: Int): Exam = service.getExam(id).unwrap()

    override suspend fun getExamList(): List<Exam> = service.getExamList().unwrap()

    override suspend fun getQuestionList(id: Int): List<Question> =
        service.getQuestionList(id).unwrap()

    override suspend fun sendExamImage(image: MultipartBody) = service.sendExamImage(image)
}
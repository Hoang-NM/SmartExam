package hoang.nguyenminh.smartexam.repository.cloud

import hoang.nguyenminh.smartexam.model.authentication.LoginRequest
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.model.exam.Question
import okhttp3.MultipartBody

interface SmartExamCloudRepository {

    suspend fun login(param: LoginRequest): UserInfo

    suspend fun getExam(id: Int): Exam

    suspend fun getExamList(): List<Exam>

    suspend fun getQuestionList(id: Int): List<Question>

    suspend fun sendExamImage(image: MultipartBody)
}
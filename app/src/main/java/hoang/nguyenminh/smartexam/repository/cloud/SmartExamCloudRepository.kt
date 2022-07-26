package hoang.nguyenminh.smartexam.repository.cloud

import hoang.nguyenminh.smartexam.model.ResultWrapper
import hoang.nguyenminh.smartexam.model.authentication.LoginRequest
import hoang.nguyenminh.smartexam.model.authentication.UpdateUserInfoRequest
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.model.exam.*
import hoang.nguyenminh.smartexam.model.main.HomeInfo

interface SmartExamCloudRepository {

    suspend fun login(param: LoginRequest): UserInfo

    suspend fun getHomeInfo(param: Int): HomeInfo

    suspend fun getExam(id: Int): Exam

    suspend fun getExamList(param: GetExamListRequest): List<Exam>

    suspend fun getQuestionList(id: Int): List<Question>

    suspend fun sendExamImage(params: SubmitExamImageRequest)

    suspend fun submitExam(param: SubmitExamRequest)

    suspend fun getExamAnswer(param: GetExamAnswerRequest): ExamAnswer

    suspend fun updateUserInfo(params: UpdateUserInfoRequest)
}
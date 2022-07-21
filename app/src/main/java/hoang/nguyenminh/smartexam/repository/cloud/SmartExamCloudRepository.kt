package hoang.nguyenminh.smartexam.repository.cloud

import hoang.nguyenminh.smartexam.model.ResultWrapper
import hoang.nguyenminh.smartexam.model.authentication.LoginRequest
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.model.exam.*

interface SmartExamCloudRepository {

    suspend fun login(param: LoginRequest): ResultWrapper<UserInfo>

    suspend fun getExam(id: Int): ResultWrapper<Exam>

    suspend fun getExamList(param: GetExamListRequest): ResultWrapper<List<Exam>>

    suspend fun getQuestionList(id: Int): ResultWrapper<List<Question>>

    suspend fun sendExamImage(params: SubmitExamImageRequest): ResultWrapper<Unit>

    suspend fun submitExam(param: SubmitExamRequest): ResultWrapper<Unit>

    suspend fun getExamAnswer(param: GetExamAnswerRequest): ResultWrapper<ExamAnswer>
}
package hoang.nguyenminh.smartexam.network

import hoang.nguyenminh.smartexam.model.BaseResponse
import hoang.nguyenminh.smartexam.model.authentication.LoginRequest
import hoang.nguyenminh.smartexam.model.authentication.UpdateUserInfoRequest
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.model.exam.ExamAnswer
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.model.exam.SubmitExamRequest
import hoang.nguyenminh.smartexam.model.main.HomeInfo
import okhttp3.MultipartBody
import retrofit2.http.*

interface SmartExamCloudService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<UserInfo>

    @GET("get-student")
    suspend fun getHomeInfo(@Query("userId") userId: Int): BaseResponse<HomeInfo>

    @GET("get-exam")
    suspend fun getExam(@Query("id") id: Int): BaseResponse<Exam>

    @GET("get-exam")
    suspend fun getExamList(@QueryMap params: Map<String, String>): BaseResponse<List<Exam>>

    @GET("get-question")
    suspend fun getQuestionList(@Query("id") id: Int): BaseResponse<List<Question>>

    @POST("upload-img")
    @Multipart
    suspend fun sendExamImage(
        @Part image: MultipartBody.Part,
        @Query("examId") examId: Int,
        @Query("studentId") studentId: Int
    ): BaseResponse<Unit>

    @PUT("save-exam")
    suspend fun submitExam(@Body request: SubmitExamRequest): BaseResponse<Unit>

    @GET("get-exam-ans")
    suspend fun getExamAnswer(@QueryMap params: Map<String, String>): BaseResponse<ExamAnswer>

    @PUT("edit-user")
    suspend fun updateUserInfo(@Body request: UpdateUserInfoRequest): BaseResponse<Unit>
}
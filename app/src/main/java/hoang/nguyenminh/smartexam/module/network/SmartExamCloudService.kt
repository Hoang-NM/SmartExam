package hoang.nguyenminh.smartexam.module.network

import hoang.nguyenminh.smartexam.model.BaseResponse
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.model.exam.Question
import retrofit2.http.GET
import retrofit2.http.Query

interface SmartExamCloudService {

    @GET("get-exam")
    suspend fun getExam(@Query("id") id: Int): BaseResponse<Exam>

    @GET("get-exam?id=ALL")
    suspend fun getExamList(): BaseResponse<List<Exam>>

    @GET("get-question")
    suspend fun getQuestionList(@Query("id") id: Int): BaseResponse<List<Question>>
}
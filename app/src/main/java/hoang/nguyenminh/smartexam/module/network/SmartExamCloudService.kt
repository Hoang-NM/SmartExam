package hoang.nguyenminh.smartexam.module.network

import hoang.nguyenminh.smartexam.model.exam.Exam
import retrofit2.http.GET
import retrofit2.http.Query

interface SmartExamCloudService {

    @GET("/get-exam")
    suspend fun getExam(@Query("id") id: Int): Exam
}
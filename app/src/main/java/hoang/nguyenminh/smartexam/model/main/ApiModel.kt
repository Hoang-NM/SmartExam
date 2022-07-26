package hoang.nguyenminh.smartexam.model.main

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import hoang.nguyenminh.smartexam.model.exam.Exam

data class HomeInfo(
    @SerializedName("allExam") @Expose val totalExams: Int,
    @SerializedName("completed") @Expose val completedExams: Int,
    @SerializedName("unCompleted") @Expose val todoExams: List<Exam>? = null,
    @SerializedName("resultExam") @Expose val examResults: List<ExamResult>? = null
) {

    fun getTodoExamsCount() = todoExams?.size ?: 0
}

data class ExamResult(
    @SerializedName("examId") @Expose val examId: Int,
    @SerializedName("true") @Expose val trueCount: String,
    @SerializedName("all") @Expose val all: String,
)
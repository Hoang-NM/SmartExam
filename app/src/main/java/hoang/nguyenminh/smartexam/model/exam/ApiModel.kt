package hoang.nguyenminh.smartexam.model.exam

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import hoang.nguyenminh.base.util.DateTimeXs
import hoang.nguyenminh.smartexam.model.IQueryMapParam
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exam(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("time") @Expose val timeLimit: Long = 60 * DateTimeXs.MINUTE,
    @SerializedName("name") @Expose val name: String = "",
    @SerializedName("subject") @Expose val subject: String? = null,
    @SerializedName("status") @Expose val status: String? = null,
    @SerializedName("createdAt") @Expose val createdAt: String? = null,
    @SerializedName("result") @Expose val result: String? = null
) : Parcelable {

    fun toExamModel(): ExamModel = ExamModel(id, name, timeLimit, creationDate = createdAt)
}

@Parcelize
data class Question(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("content") @Expose val content: String,
    @SerializedName("optionA") @Expose val optionA: String,
    @SerializedName("optionB") @Expose val optionB: String,
    @SerializedName("optionC") @Expose val optionC: String,
    @SerializedName("optionD") @Expose val optionD: String,
) : Parcelable {

    @IgnoredOnParcel
    val choiceA: Choice
        get() = Choice(ChoiceIndex.A, optionA)

    @IgnoredOnParcel
    val choiceB: Choice
        get() = Choice(ChoiceIndex.B, optionB)

    @IgnoredOnParcel
    val choiceC: Choice
        get() = Choice(ChoiceIndex.C, optionC)

    @IgnoredOnParcel
    val choiceD: Choice
        get() = Choice(ChoiceIndex.D, optionD)

    @IgnoredOnParcel
    val choices: List<Choice>
        get() = listOf(choiceA, choiceB, choiceC, choiceD)

    fun toQuestionModel(): QuestionModel = QuestionModel(id, content, choices)
}

data class SubmitExamRequest(
    @SerializedName("studentId") @Expose var studentId: Int = 0,
    @SerializedName("examId") @Expose var examId: Int = 0,
    @SerializedName("data") @Expose var answers: List<Answer> = emptyList()
)

data class Answer(
    @SerializedName("questionId") @Expose var questionId: Int = 0,
    @SerializedName("studentAnswer") @Expose var answer: String = ""
)

data class ExamImageQuery(
    @SerializedName("studentId") @Expose var studentId: Int = 0,
    @SerializedName("examId") @Expose var examId: Int = 1,
)

data class SubmitExamImageRequest(
    val query: ExamImageQuery = ExamImageQuery(),
    var path: String = ""
)

data class GetExamAnswerRequest(
    var examId: Int = 0,
    var studentId: Int = 0
) : IQueryMapParam {

    override fun putQueries(map: MutableMap<String, String>) {
        map.apply {
            put("examId", examId.toString())
            put("studentId", studentId.toString())
        }
    }
}

data class GetExamListRequest(
    var userId: Int = 0,
    var status: Int = ExamStatus.DONE.value
) : IQueryMapParam {

    override fun putQueries(map: MutableMap<String, String>) {
        map.apply {
            put("userId", userId.toString())
            put("status", status.toString())
        }
    }
}

data class ExamAnswer(
    @SerializedName("examId") @Expose var examId: Int = 1,
    @SerializedName("studentId") @Expose var studentId: Int = 1,
    @SerializedName("name") @Expose val name: String = "",
    @SerializedName("subject") @Expose val subject: String? = null,
    @SerializedName("result") @Expose var result: String = "20/20",
    @SerializedName("ansList") @Expose var ansList: List<Answer>
) {
    fun toExamModel(): ExamModel {
        return ExamModel(examId, name, subject = subject ?: "", result = result)
    }
}
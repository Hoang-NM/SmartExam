package hoang.nguyenminh.smartexam.model.exam

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import hoang.nguyenminh.smartexam.model.IQueryMapParam
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exam(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("time") @Expose val timeLimit: Long,
    @SerializedName("name") @Expose val name: String = "",
    @SerializedName("subject") @Expose val subject: String? = null,
    @SerializedName("status") @Expose val status: Int? = 0,
    @SerializedName("createdAt") @Expose val createdAt: String? = null,
    @SerializedName("result") @Expose val result: String? = null
) : Parcelable {

    fun toExamModel(): ExamModel = ExamModel(
        id,
        name,
        timeLimit,
        result = result ?: "",
        status = ExamStatus.fromIntConstant(status) ?: ExamStatus.INITIALIZE
    )
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
) {

    fun toAnswerModel(): AnswerModel {
        val indexes = answer.split("", limit = 4)
        val choices = listOf(
            Choice(ChoiceIndex.A, "", indexes.firstOrNull { it == ChoiceIndex.A.name } != null),
            Choice(ChoiceIndex.B, "", indexes.firstOrNull { it == ChoiceIndex.B.name } != null),
            Choice(ChoiceIndex.C, "", indexes.firstOrNull { it == ChoiceIndex.C.name } != null),
            Choice(ChoiceIndex.D, "", indexes.firstOrNull { it == ChoiceIndex.D.name } != null),
        )
        return AnswerModel(questionId, choices)
    }
}

data class ExamImageQuery(
    @SerializedName("studentId") @Expose var studentId: Int = 0,
    @SerializedName("examId") @Expose var examId: Int = 0,
)

data class SubmitExamImageRequest(
    val query: ExamImageQuery = ExamImageQuery(),
    var uri: Uri = Uri.EMPTY
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
    @SerializedName("time") @Expose val timeLimit: Long,
    @SerializedName("name") @Expose val name: String = "",
    @SerializedName("subject") @Expose val subject: String? = null,
    @SerializedName("result") @Expose var result: String = "20/20",
    @SerializedName("ansList") @Expose var ansList: List<Answer>
) {
    fun toExamModel(): ExamModel {
        return ExamModel(
            examId,
            name,
            timeLimit,
            ansList.map(Answer::toAnswerModel).map(AnswerModel::toQuestionModel),
            subject = subject ?: "",
            status = ExamStatus.DONE,
            result = result
        )
    }
}
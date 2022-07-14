package hoang.nguyenminh.smartexam.model.exam

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import hoang.nguyenminh.base.util.DateTimeXs
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

data class Exam(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("time") @Expose val timeLimit: Long = 60 * DateTimeXs.MINUTE,
    @SerializedName("name") @Expose val name: String = "",
    @SerializedName("subject") @Expose val subject: String? = null,
    @SerializedName("status") @Expose val status: String? = null,
    @SerializedName("createdAt") @Expose val createdAt: String? = null,
    @SerializedName("result") @Expose val result: String = "50/50",
    val questions: List<Question> = listOf()
) {

    fun toExamModel(): ExamModel =
        ExamModel(id, name, timeLimit, questions.map(Question::toQuestionModel), createdAt, result)
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
    var choiceA = Choice(ChoiceIndex.A, optionA)

    @IgnoredOnParcel
    var choiceB = Choice(ChoiceIndex.B, optionB)

    @IgnoredOnParcel
    var choiceC = Choice(ChoiceIndex.C, optionC)

    @IgnoredOnParcel
    var choiceD = Choice(ChoiceIndex.D, optionD)

    @IgnoredOnParcel
    val choices = listOf(choiceA, choiceB, choiceC, choiceD)

    fun toQuestionModel(): QuestionModel = QuestionModel(id, content, choices)
}

data class SubmitExamRequest(
    @SerializedName("answers") @Expose var answers: List<Answer> = emptyList()
)

data class Answer(
    @SerializedName("studentAnswer") @Expose var answer: String = "",
    @SerializedName("questionId") @Expose var questionId: Int = 0,
    @SerializedName("studentId") @Expose var studentId: Int = 0,
    @SerializedName("examId") @Expose var examId: Int = 0,
) {

    fun fromAnswerModel(answer: AnswerModel): Answer {
        val studentAnswer = answer.choices.joinToString { it.index.identity }
        return Answer(questionId = answer.id, answer = studentAnswer)
    }
}

data class SubmitExamImageRequest(
    val path: String
)
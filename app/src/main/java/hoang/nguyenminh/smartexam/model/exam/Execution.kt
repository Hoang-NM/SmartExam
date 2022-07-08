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
        ExamModel(id, timeLimit, questions.map(Question::toQuestionModel))
}

data class SubmitExamRequest(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("answers") @Expose val answers: List<Answer>
)

data class ExamModel(
    val id: Int,
    var timeLimit: Long,
    val questions: List<QuestionModel> = listOf()
)

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

@Parcelize
data class QuestionModel(
    val id: Int,
    val content: String,
    var choices: List<Choice> = listOf()
) : Parcelable

data class QuestionIndex(
    val index: Int,
    val isSelected: Boolean = false,
    val isAnswered: Boolean = false
)

data class Answer(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("answer") @Expose val answer: Int
)

@Parcelize
data class Choice(
    @SerializedName("index") @Expose val index: ChoiceIndex,
    @SerializedName("content") @Expose val content: String,
    var isSelected: Boolean = false
) : Parcelable

enum class ChoiceIndex(val identity: String) {
    A("A"), B("B"), C("C"), D("D");

    companion object {
        fun fromStringConstant(identity: String): ChoiceIndex? = values().firstOrNull {
            it.identity == identity
        }
    }
}

data class SubmitExamImageRequest(
    val path: String
)
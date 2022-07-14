package hoang.nguyenminh.smartexam.model.exam

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExamModel(
    val id: Int,
    val name: String = "",
    var timeLimit: Long,
    val questions: List<QuestionModel> = listOf(),
    val creationDate: String? = null,
    val result: String = "50/50",
) : Parcelable

@Parcelize
data class QuestionModel(
    val id: Int,
    val content: String,
    var choices: List<Choice> = listOf()
) : Parcelable {

    fun toAnswerModel(): AnswerModel = AnswerModel(id, choices)
}

data class QuestionIndex(
    val index: Int,
    val isSelected: Boolean = false,
    val isAnswered: Boolean = false
)

data class AnswerModel(
    val id: Int,
    val choices: List<Choice> = listOf()
) {
    fun toAnswer(): Answer =
        Answer(questionId = id, answer = choices.joinToString { it.index.identity })
}

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
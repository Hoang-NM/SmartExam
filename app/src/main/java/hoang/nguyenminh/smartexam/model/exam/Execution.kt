package hoang.nguyenminh.smartexam.model.exam

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Exam(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("time") @Expose val timeLimit: Long,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("subject") @Expose val subject: String,
    @SerializedName("status") @Expose val status: String,
    @SerializedName("createdAt") @Expose val createdAt: String,
    @SerializedName("result") @Expose val result: String = "50/50"
)

data class SubmitExamRequest(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("answers") @Expose val answers: List<Answer>
)

@Parcelize
data class Question(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("content") @Expose val question: String,
    @SerializedName("optionA") @Expose val optionA: String,
    @SerializedName("optionB") @Expose val optionB: String,
    @SerializedName("optionC") @Expose val optionC: String,
    @SerializedName("optionD") @Expose val optionD: String,
    @SerializedName("choices") @Expose val choices: List<Choice>
) : Parcelable {

    fun combineOptions(): List<String> = mutableListOf(optionA, optionB, optionC, optionD)
}

data class Answer(
    @SerializedName("id") @Expose val id: Int,
    @SerializedName("answer") @Expose val answer: Int
)

@Parcelize
data class Choice(
    @SerializedName("index") @Expose val index: Int,
    @SerializedName("content") @Expose val content: String,
    var isSelected: Boolean = false
) : Parcelable

enum class ChoiceIndex(val index: Int) {
    A(0), B(1), C(2), D(3);

    companion object {
        fun fromIntConstant(index: Int): ChoiceIndex? = values().firstOrNull {
            it.index == index
        }
    }
}

data class SubmitExamImageRequest(
    val path: String
)
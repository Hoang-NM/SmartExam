package hoang.nguyenminh.smartexam.model.exam

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class Exam(
    @Json(name = "id") val id: Int,
    @Json(name = "time") val timeLimit: Long,
    @Json(name = "name") val name: String,
    @Json(name = "subject") val subject: String,
    @Json(name = "status") val status: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "result") val result: String = "50/50"
)

@JsonClass(generateAdapter = true)
data class SubmitExamRequest(
    @Json(name = "id") val id: Int,
    @Json(name = "answers") val answers: List<Answer>
)

@Parcelize
@JsonClass(generateAdapter = true)
data class Question(
    @Json(name = "id") val id: Int,
    @Json(name = "content") val question: String,
    @Json(name = "optionA") val optionA: String,
    @Json(name = "optionB") val optionB: String,
    @Json(name = "optionC") val optionC: String,
    @Json(name = "optionD") val optionD: String,
    @Json(name = "choices") val choices: List<Choice>
) : Parcelable {

    fun combineOptions(): List<String> = mutableListOf(optionA, optionB, optionC, optionD)
}

@JsonClass(generateAdapter = true)
data class Answer(
    @Json(name = "id") val id: Int,
    @Json(name = "answer") val answer: Int
)

@Parcelize
@JsonClass(generateAdapter = true)
data class Choice(
    @Json(name = "index") val index: Int,
    @Json(name = "content") val content: String,
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
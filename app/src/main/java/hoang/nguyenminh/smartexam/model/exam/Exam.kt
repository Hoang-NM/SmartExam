package hoang.nguyenminh.smartexam.model.exam

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class Exam(
    @Json(name = "id") val id: Int,
    @Json(name = "timeLimit") val timeLimit: Long,
    @Json(name = "questions") val questions: List<Question>
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
    @Json(name = "question") val question: String,
    @Json(name = "choices") val choices: List<Choice>
) : Parcelable

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
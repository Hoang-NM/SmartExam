package hoang.nguyenminh.smartexam.model.exam

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Exam(
    val id: Int,
    val timeLimit: Long,
    val questions: List<Question>
)

data class SubmitExamRequest(
    val id: Int,
    val answers: List<Answer>
)

@Parcelize
data class Question(
    val id: Int,
    val question: String,
    val choices: List<Choice>
) : Parcelable

data class Answer(
    val id: Int,
    val answer: Int
)

@Parcelize
data class Choice(
    val index: Int,
    val content: String
) : Parcelable

enum class ChoiceIndex(val index: Int) {
    A(0), B(1), C(2), D(3);

    companion object {
        fun fromIntConstant(index: Int): ChoiceIndex? = values().firstOrNull {
            it.index == index
        }
    }
}
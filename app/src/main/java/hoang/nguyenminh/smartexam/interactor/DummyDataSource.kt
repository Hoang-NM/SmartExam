package hoang.nguyenminh.smartexam.interactor

import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.model.exam.*
import hoang.nguyenminh.smartexam.model.main.HomeInfo

fun userInfo() = UserInfo(
    0,
    "hoang@gmail.com",
    "Hoang",
    "Nguyen",
    "Hanoi, Vietnam",
    "KTMT.07",
    0,
    "01-01-99",
    "0123456789"
)

fun todoExams() = (1..2).map {
    Exam(
        it,
        45,
        "Important Exam $it",
        "English",
        ExamStatus.IN_PROGRESS.value,
        "2022-08-04 15:00:00",
        null
    )
}

fun doneExams() = (3..6).map {
    Exam(
        it,
        15 * it.toLong(),
        "Important Exam $it",
        "English",
        ExamStatus.DONE.value,
        "2022-08-04 15:00:00",
        "4$it/50"
    )
}

fun detailExam() = Exam(
    1, 45, "Important Exam 1", "English", ExamStatus.DONE.value, "2022-08-04 15:00:00", "44/50"
)

fun detailExamHistory() = ExamAnswer(
    1, 1, 45, "Important Exam 1", "English", "44/50", answers()
)

fun questions() = (1..4).flatMap { questionsSource() }

fun questionsSource() = listOf(
    Question(
        1,
        "There's a ________ range of issues that we need to discuss as soon as possible.",
        "far",
        "ample",
        "wide",
        "high"
    ),
    Question(
        2,
        "Lance is ________ knowledgeable on this subject.",
        "smartly",
        "powerfully",
        "firmly",
        "highly"
    ),
    Question(
        3,
        "I need a good explanation of all the costs ________ in buying a new car.",
        "affected",
        "involved",
        "concerned",
        "implied"
    ),
    Question(
        4,
        "There was a ________ debate about the Middle East, then they moved to a vote.",
        "lively",
        "flexible",
        "main",
        "nimble"
    ),
    Question(
        5,
        "The doctor told him to lose weight quickly or pay the ________ later in life.",
        "fee",
        "fine",
        "price",
        "cost"
    ),
)

fun answers() = (1..20).map {
    Answer(it, answerIndexes().random())
}

fun answerIndexes() =
    listOf("A", "B", "C", "D", "AB", "AC", "AD", "BC", "BD", "CD", "ABC", "ABD", "ABCD")

fun homeInfo() = HomeInfo(6, 4, todoExams(), null)


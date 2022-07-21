package hoang.nguyenminh.smartexam.model.exam

object ExamAction {
    const val EXECUTION = 0
    const val VIEW_RESULT = 1
}

enum class ExamStatus(val value: String) {
    INITIALIZE("INITIALIZE"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE")
}
package hoang.nguyenminh.smartexam.model.exam

object ExamAction {
    const val EXECUTION = 0
    const val VIEW_RESULT = 1
}

enum class ExamStatus(val value: Int) {
    INITIALIZE(0),
    IN_PROGRESS(1),
    DONE(2)
}
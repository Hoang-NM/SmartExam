package hoang.nguyenminh.smartexam.module.configuration

import hoang.nguyenminh.smartexam.model.exam.ExamModel
import hoang.nguyenminh.smartexam.model.exam.QuestionModel

interface ConfigurationManager {

    fun saveCurrentExam(exam: ExamModel)

    fun saveCurrentAnswer(question: QuestionModel)

    fun getUnfinishedExam(): ExamModel?

    fun clearFinishedExam()
}
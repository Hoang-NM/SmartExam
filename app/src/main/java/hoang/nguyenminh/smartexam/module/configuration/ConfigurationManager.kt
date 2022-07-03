package hoang.nguyenminh.smartexam.module.configuration

import hoang.nguyenminh.smartexam.model.exam.Exam

interface ConfigurationManager {

    fun saveCurrentExam(exam: String)

    fun saveCurrentAnswer()

    fun getUnfinishedExam(): String?
}
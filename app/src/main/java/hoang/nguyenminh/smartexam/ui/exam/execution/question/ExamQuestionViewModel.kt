package hoang.nguyenminh.smartexam.ui.exam.execution.question

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.model.exam.QuestionModel
import hoang.nguyenminh.smartexam.module.configuration.ConfigurationManager
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExamQuestionViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var configurationManager: ConfigurationManager

    fun saveSelectedChoice(question: QuestionModel) {
        val selectedChoices = question.choices.filter { it.isSelected }
        val selectedChoiceInString = selectedChoices.map { it.index.identity }.joinToString("-")
        Timber.e("Save answer: ${question.content}: $selectedChoiceInString")
        configurationManager.saveCurrentAnswer(question)
    }
}
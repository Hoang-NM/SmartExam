package hoang.nguyenminh.smartexam.ui.exam.execution.question

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.module.configuration.ConfigurationManager
import hoang.nguyenminh.smartexam.module.credential.CredentialManager
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExamQuestionViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var configurationManager: ConfigurationManager

    fun saveSelectedChoice(index: Int, answer: String) {
        Timber.e("Save answer: $index: $answer")
        configurationManager.saveCurrentExam("$index: $answer")
    }
}
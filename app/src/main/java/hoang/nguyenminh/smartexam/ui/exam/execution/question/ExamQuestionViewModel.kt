package hoang.nguyenminh.smartexam.ui.exam.execution.question

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExamQuestionViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    fun saveSelectedChoice(answer: String) {
        Timber.e("Save answer: answer = $answer")
    }
}
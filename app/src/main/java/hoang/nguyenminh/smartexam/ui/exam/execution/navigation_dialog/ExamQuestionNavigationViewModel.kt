package hoang.nguyenminh.smartexam.ui.exam.execution.navigation_dialog

import android.app.Application
import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.model.exam.QuestionIndex
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ExamQuestionNavigationViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    val flowOfQuestionIndexes = MutableStateFlow<List<QuestionIndex>>(emptyList())

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args?.let {
            ExamQuestionNavigationDialogArgs.fromBundle(it)
        }?.let { navArgs ->
            flowOfQuestionIndexes.value = (1..navArgs.count).map {
                QuestionIndex(it, it == navArgs.index, false)
            }
        }
    }
}
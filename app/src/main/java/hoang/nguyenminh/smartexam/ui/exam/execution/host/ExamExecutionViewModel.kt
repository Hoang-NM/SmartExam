package hoang.nguyenminh.smartexam.ui.exam.execution.host

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetQuestionListUseCase
import hoang.nguyenminh.smartexam.model.exam.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamExecutionViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var useCase: GetQuestionListUseCase

    val flowOfQuestions = MutableStateFlow<List<Question>?>(null)

    override fun onReady() {
        super.onReady()
        flowOfQuestions.value ?: viewModelScope.launch(Dispatchers.IO) {
            flowOfQuestions.value = useCase(coroutineContext, 50)
        }
    }
}
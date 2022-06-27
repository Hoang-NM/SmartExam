package hoang.nguyenminh.smartexam.ui.exam.execution.host

import android.app.Application
import android.os.Bundle
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

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args?.let {
            ExamExecutionFragmentArgs.fromBundle(it)
        }?.let {
            flowOfQuestions.value ?: viewModelScope.launch(Dispatchers.IO) {
                flowOfQuestions.value = useCase(coroutineContext, it.id)
            }
        }
    }
}
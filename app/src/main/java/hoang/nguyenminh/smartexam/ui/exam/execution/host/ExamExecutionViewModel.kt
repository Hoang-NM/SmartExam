package hoang.nguyenminh.smartexam.ui.exam.execution.host

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.base.util.DateTimeXs
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetQuestionListUseCase
import hoang.nguyenminh.smartexam.model.exam.ExamModel
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.module.configuration.ConfigurationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamExecutionViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var configurationManager: ConfigurationManager

    @Inject
    lateinit var useCase: GetQuestionListUseCase

    val flowOfExam = MutableStateFlow<ExamModel?>(null)

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args?.let {
            ExamExecutionFragmentArgs.fromBundle(it)
        }?.let {
            flowOfExam.value ?: viewModelScope.launch(Dispatchers.IO) {
                val questions = useCase(coroutineContext, it.id).map(Question::toQuestionModel)
                flowOfExam.value = ExamModel(it.id, 10 * DateTimeXs.MINUTE, questions)
            }
        }
    }

    fun clearSavedExamProgress() {
        configurationManager.clearFinishedExam()
    }
}
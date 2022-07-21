package hoang.nguyenminh.smartexam.ui.exam.execution.host

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetQuestionListUseCase
import hoang.nguyenminh.smartexam.model.exam.ExamModel
import hoang.nguyenminh.smartexam.model.exam.ExamStatus
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.model.exam.QuestionModel
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

    var originalQuestionList = listOf<QuestionModel>()

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args ?: return
        ExamExecutionFragmentArgs.fromBundle(args).let { args ->
            when (args.status) {
                ExamStatus.INITIALIZE -> {
                    flowOfExam.value ?: viewModelScope.launch(Dispatchers.IO) {
                        fetchData(args.id)
                    }
                }
                ExamStatus.IN_PROGRESS -> {
                    flowOfExam.value ?: run {
                        flowOfExam.value = configurationManager.getUnfinishedExam()
                    }
                }
                else -> return
            }
        }
    }

    private fun fetchData(id: Int) = viewModelScope.launch {
        execute(useCase, id, onSuccess = {
            originalQuestionList = it.map(Question::toQuestionModel)
            val questions = originalQuestionList.shuffled()
            flowOfExam.value =
                ExamModel(id, questions = questions).also {
                    configurationManager.saveCurrentExam(it)
                }
        })
    }
}
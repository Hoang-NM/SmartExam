package hoang.nguyenminh.smartexam.ui.exam.execution.host

import android.app.Application
import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetQuestionListUseCase
import hoang.nguyenminh.smartexam.model.exam.ExamModel
import hoang.nguyenminh.smartexam.model.exam.ExamStatus
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.model.exam.QuestionModel
import hoang.nguyenminh.smartexam.util.module.configuration.ConfigurationManager
import kotlinx.coroutines.flow.MutableStateFlow
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
        ExamExecutionFragmentArgs.fromBundle(args).let {
            when (it.status) {
                ExamStatus.INITIALIZE -> {
                    flowOfExam.value ?: fetchData(it.id)
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

    private fun fetchData(id: Int) {
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
package hoang.nguyenminh.smartexam.ui.exam.submit

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.SubmitExamUseCase
import hoang.nguyenminh.smartexam.model.exam.AnswerModel
import hoang.nguyenminh.smartexam.model.exam.ExamModel
import hoang.nguyenminh.smartexam.model.exam.QuestionModel
import hoang.nguyenminh.smartexam.model.exam.SubmitExamRequest
import hoang.nguyenminh.smartexam.util.module.configuration.ConfigurationManager
import hoang.nguyenminh.smartexam.util.module.credential.CredentialManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamSubmitViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var credentialManager: CredentialManager

    @Inject
    lateinit var configurationManager: ConfigurationManager

    @Inject
    lateinit var useCase: SubmitExamUseCase

    private val request by lazy {
        SubmitExamRequest()
    }

    val flowOfDetail = MutableStateFlow<ExamModel?>(null)

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args?.let {
            ExamSubmitFragmentArgs.fromBundle(it)
        }?.let { arguments ->
            flowOfDetail.value = arguments.exam
            val answers = arguments.exam.questions.map(QuestionModel::toAnswerModel)
            val userId = credentialManager.getAuthenticationInfo()?.id ?: 0

            request.apply {
                studentId = userId
                examId = arguments.exam.id
                this.answers = answers.map(AnswerModel::toAnswer)
            }
        }
    }

    fun submitExam() = viewModelScope.launch {
        execute(useCase, request, onSuccess = {
            configurationManager.clearFinishedExam()
        }, onError = { _, _ -> true })
    }
}
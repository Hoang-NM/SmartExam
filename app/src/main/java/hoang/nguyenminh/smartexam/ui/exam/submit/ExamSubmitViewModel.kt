package hoang.nguyenminh.smartexam.ui.exam.submit

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetQuestionListUseCase
import hoang.nguyenminh.smartexam.interactor.exam.SubmitExamUseCase
import hoang.nguyenminh.smartexam.model.exam.AnswerModel
import hoang.nguyenminh.smartexam.model.exam.ExamModel
import hoang.nguyenminh.smartexam.model.exam.QuestionModel
import hoang.nguyenminh.smartexam.model.exam.SubmitExamRequest
import hoang.nguyenminh.smartexam.module.configuration.ConfigurationManager
import hoang.nguyenminh.smartexam.module.credential.CredentialManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    lateinit var useCase: GetQuestionListUseCase

    @Inject
    lateinit var submitUseCase: SubmitExamUseCase

    private val request by lazy {
        SubmitExamRequest()
    }

    private val flowOfDetail = MutableStateFlow<ExamModel?>(null)

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args?.let {
            ExamSubmitFragmentArgs.fromBundle(it)
        }?.let { arguments ->
            flowOfDetail.value = arguments.exam
            val answers = arguments.exam.questions.map(QuestionModel::toAnswerModel)
            val userId = credentialManager.getAuthenticationInfo()?.userId ?: 0

            request.answers = answers.map(AnswerModel::toAnswer).onEach {
                it.studentId = userId
                it.examId = arguments.exam.id
            }
        }
    }

    fun getDetail(): StateFlow<ExamModel?> = flowOfDetail

    fun submitExam() = viewModelScope.launch(Dispatchers.IO) {
        submitUseCase(coroutineContext, request)
        configurationManager.clearFinishedExam()
    }
}
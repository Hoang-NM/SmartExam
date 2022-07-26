package hoang.nguyenminh.smartexam.ui.exam.detail

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetExamAnswerUseCase
import hoang.nguyenminh.smartexam.interactor.exam.GetExamDetailUseCase
import hoang.nguyenminh.smartexam.model.exam.ExamAction
import hoang.nguyenminh.smartexam.model.exam.ExamModel
import hoang.nguyenminh.smartexam.model.exam.GetExamAnswerRequest
import hoang.nguyenminh.smartexam.module.credential.CredentialManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamDetailViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var credentialManager: CredentialManager

    @Inject
    lateinit var useCase: GetExamDetailUseCase

    @Inject
    lateinit var resultUseCase: GetExamAnswerUseCase

    val request by lazy {
        GetExamAnswerRequest()
    }

    val flowOfDetail = MutableStateFlow<ExamModel?>(null)

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args?.let {
            ExamDetailFragmentArgs.fromBundle(it)
        }?.let {
            if (it.action == ExamAction.VIEW_RESULT) {
                request.apply {
                    examId = it.exam.id
                    studentId = credentialManager.getAuthenticationInfo()?.id ?: 0
                }
                getExamResult()
                return@let
            }
            flowOfDetail.value = it.exam
        }
    }

    fun getDetail(): StateFlow<ExamModel?> = flowOfDetail

    private fun getExamResult() {
        viewModelScope.launch {
            flowOfDetail.value = execute(resultUseCase, request).toExamModel()
        }
    }
}
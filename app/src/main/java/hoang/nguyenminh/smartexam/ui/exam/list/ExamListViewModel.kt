package hoang.nguyenminh.smartexam.ui.exam.list

import android.app.Application
import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetExamListUseCase
import hoang.nguyenminh.smartexam.model.exam.*
import hoang.nguyenminh.smartexam.util.module.credential.CredentialManager
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ExamListViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var credentialManager: CredentialManager

    @Inject
    lateinit var useCase: GetExamListUseCase

    private val request by lazy {
        GetExamListRequest()
    }

    val flowOfExamList = MutableStateFlow<List<ExamModel>?>(null)

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args?.let {
            ExamListFragmentArgs.fromBundle(it)
        }?.let {
            request.apply {
                userId = credentialManager.getAuthenticationInfo()?.id ?: 0
                status = when (it.action) {
                    ExamAction.EXECUTION -> ExamStatus.IN_PROGRESS.value
                    ExamAction.VIEW_RESULT -> ExamStatus.DONE.value
                    else -> return@apply
                }
            }
        }
    }

    override fun onReady() {
        super.onReady()
        flowOfExamList.value ?: execute(useCase, request, onSuccess = {
            flowOfExamList.value = it.map(Exam::toExamModel)
        })
    }
}
package hoang.nguyenminh.smartexam.ui.exam.photo.display

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.SendExamImageUseCase
import hoang.nguyenminh.smartexam.model.exam.SubmitExamImageRequest
import hoang.nguyenminh.smartexam.module.credential.CredentialManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDisplayViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var credentialManager: CredentialManager

    @Inject
    lateinit var useCase: SendExamImageUseCase

    private val request by lazy {
        SubmitExamImageRequest()
    }

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args?.let {
            ImageDisplayFragmentArgs.fromBundle(it)
        }?.let { arguments ->
            val studentId = credentialManager.getAuthenticationInfo()?.id ?: 0
            request.apply {
                path = arguments.path
                request.query.examId = arguments.examId
                request.query.studentId = studentId
            }
        }
    }

    fun sendExamImage() = viewModelScope.launch(Dispatchers.IO) {
        useCase(coroutineContext, request)
    }
}
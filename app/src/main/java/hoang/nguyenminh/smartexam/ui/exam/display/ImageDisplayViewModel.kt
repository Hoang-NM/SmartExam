package hoang.nguyenminh.smartexam.ui.exam.display

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.SendExamImageUseCase
import hoang.nguyenminh.smartexam.model.exam.ExamInfo
import hoang.nguyenminh.smartexam.model.exam.SubmitExamImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDisplayViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var useCase: SendExamImageUseCase

    fun sendExamImage(path: String) = viewModelScope.launch(Dispatchers.IO) {
        useCase(coroutineContext, SubmitExamImageRequest(ExamInfo(), path))
    }
}
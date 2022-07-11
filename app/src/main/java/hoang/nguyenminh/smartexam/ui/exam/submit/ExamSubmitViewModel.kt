package hoang.nguyenminh.smartexam.ui.exam.submit

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.SubmitExamUseCase
import hoang.nguyenminh.smartexam.model.exam.ExamModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamSubmitViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var useCase: SubmitExamUseCase

    private val flowOfDetail = MutableStateFlow<ExamModel?>(null)

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args?.let {
            ExamSubmitFragmentArgs.fromBundle(it)
        }?.let {
            flowOfDetail.value = it.exam
        }
    }

    fun getDetail(): StateFlow<ExamModel?> = flowOfDetail

    fun submitExam() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.invoke(coroutineContext, Unit)
        }
    }
}
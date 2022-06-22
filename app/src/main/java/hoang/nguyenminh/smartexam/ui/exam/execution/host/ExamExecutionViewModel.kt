package hoang.nguyenminh.smartexam.ui.exam.execution.host

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.base.scene.BaseAndroidViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetExamQuestionUseCase
import hoang.nguyenminh.smartexam.model.exam.Exam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamExecutionViewModel @Inject constructor(application: Application) :
    BaseAndroidViewModel(application) {

    @Inject
    lateinit var useCase: GetExamQuestionUseCase

    val flowOfExam = MutableStateFlow<Exam?>(null)

    override fun onReady() {
        super.onReady()
        flowOfExam.value ?: viewModelScope.launch(Dispatchers.IO) {
            flowOfExam.value = useCase(coroutineContext, 50)
        }
    }
}
package hoang.nguyenminh.smartexam.ui.exam_execution.host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetExamQuestionUseCase
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamExecutionViewModel @Inject constructor(
    private val smartExamCloudRepository: SmartExamCloudRepository
) : ViewModel() {

    @Inject
    lateinit var useCase: GetExamQuestionUseCase

    val flowOfExam = MutableStateFlow<Exam?>(null)

    init {
        flowOfExam.value ?: viewModelScope.launch(Dispatchers.IO) {
            flowOfExam.value = useCase.getExamQuestion()
        }
    }
}
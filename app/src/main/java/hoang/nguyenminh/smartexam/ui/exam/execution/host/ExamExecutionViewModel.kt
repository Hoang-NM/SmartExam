package hoang.nguyenminh.smartexam.ui.exam.execution.host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetExamQuestionUseCase
import hoang.nguyenminh.smartexam.model.exam.Choice
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.model.exam.Question
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import hoang.nguyenminh.smartexam.util.DateTimeXs
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
            flowOfExam.value = getExamQuestion()
        }
    }

    private fun getExamQuestion(): Exam {
        val choices = (1..200).map {
            Choice(it, "Choice $it")
        }
        var startIndex = -4
        val questions = (1..50).map {
            startIndex += 4
            Question(it, "Question $it", choices.subList(startIndex, startIndex + 4))
        }
        return Exam(0, 60 * DateTimeXs.MINUTE, questions)
    }
}
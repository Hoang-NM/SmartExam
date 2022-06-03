package hoang.nguyenminh.smartexam.ui.exam_execution.question

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.model.exam.Choice
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ExamQuestionViewModel @Inject constructor(
    private val smartExamCloudRepository: SmartExamCloudRepository
) : ViewModel() {

    fun saveSelectedChoice(choice: Choice) {
        Timber.e("Save choice: ${choice.index} - ${choice.content}")
    }
}
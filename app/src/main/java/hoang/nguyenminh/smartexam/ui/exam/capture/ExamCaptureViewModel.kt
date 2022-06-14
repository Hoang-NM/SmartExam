package hoang.nguyenminh.smartexam.ui.exam.capture

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

@HiltViewModel
class ExamCaptureViewModel @Inject constructor(private val repository: SmartExamCloudRepository) :
    ViewModel()
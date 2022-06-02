package hoang.nguyenminh.smartexam.ui.authentication.register

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val smartExamCloudRepository: SmartExamCloudRepository
) : ViewModel()
package hoang.nguyenminh.smartexam.ui.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: SmartExamCloudRepository) :
    ViewModel()
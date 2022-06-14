package hoang.nguyenminh.smartexam.ui.exam.host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetExamMenuUseCase
import hoang.nguyenminh.smartexam.model.main.MenuItem
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(private val repository: SmartExamCloudRepository) :
    ViewModel() {

    @Inject
    lateinit var useCase: GetExamMenuUseCase

    val flowOfMenuItem = MutableStateFlow<List<MenuItem>?>(null)

    init {
        flowOfMenuItem.value ?: viewModelScope.launch(Dispatchers.IO) {
            flowOfMenuItem.value = useCase.getExamMenu()
        }
    }
}
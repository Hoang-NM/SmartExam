package hoang.nguyenminh.smartexam.ui.exam.menu

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetExamMenuUseCase
import hoang.nguyenminh.smartexam.model.main.MenuItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamMenuViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var useCase: GetExamMenuUseCase

    val flowOfMenuItem = MutableStateFlow<List<MenuItem>?>(null)

    override fun onReady() {
        super.onReady()
        flowOfMenuItem.value ?: viewModelScope.launch(Dispatchers.IO) {
            flowOfMenuItem.value = useCase(coroutineContext, Unit)
        }
    }
}
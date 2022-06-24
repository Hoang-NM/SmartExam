package hoang.nguyenminh.smartexam.ui.exam.history

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetExamHistoryUseCase
import hoang.nguyenminh.smartexam.model.exam.ExamHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamHistoryViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var useCase: GetExamHistoryUseCase

    val flowOfHistories = MutableStateFlow<List<ExamHistory>?>(null)

    override fun onReady() {
        super.onReady()
        flowOfHistories.value ?: viewModelScope.launch(Dispatchers.IO) {
            flowOfHistories.value = useCase(coroutineContext, Unit)
        }
    }
}
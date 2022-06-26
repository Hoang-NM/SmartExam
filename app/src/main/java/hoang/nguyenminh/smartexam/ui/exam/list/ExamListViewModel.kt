package hoang.nguyenminh.smartexam.ui.exam.list

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetExamHistoryUseCase
import hoang.nguyenminh.smartexam.model.exam.Exam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamListViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var useCase: GetExamHistoryUseCase

    val flowOfExamList = MutableStateFlow<List<Exam>?>(null)

    override fun onReady() {
        super.onReady()
        flowOfExamList.value ?: viewModelScope.launch(Dispatchers.IO) {
            flowOfExamList.value = useCase(coroutineContext, Unit)
        }
    }
}
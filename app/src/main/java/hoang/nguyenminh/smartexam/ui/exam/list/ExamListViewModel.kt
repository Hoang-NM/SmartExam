package hoang.nguyenminh.smartexam.ui.exam.list

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetExamListUseCase
import hoang.nguyenminh.smartexam.model.exam.Exam
import hoang.nguyenminh.smartexam.model.exam.ExamModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ExamListViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var useCase: GetExamListUseCase

    val flowOfExamList = MutableStateFlow<List<ExamModel>?>(null)

    override fun onReady() {
        super.onReady()
        flowOfExamList.value ?: execute(useCase, Unit, onSuccess = {
            flowOfExamList.value = it.map(Exam::toExamModel)
        })
    }
}
package hoang.nguyenminh.smartexam.ui.exam.detail

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetExamDetailUseCase
import hoang.nguyenminh.smartexam.model.exam.ExamDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamDetailViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var useCase: GetExamDetailUseCase

    private val flowOfDetail = MutableStateFlow<ExamDetail?>(null)

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args?.let {
            ExamDetailFragmentArgs.fromBundle(it)
        }?.let {
            flowOfDetail.value ?: viewModelScope.launch(Dispatchers.IO) {
                flowOfDetail.value = useCase(coroutineContext, it.id)
            }
        }
    }

    fun getDetail(): StateFlow<ExamDetail?> = flowOfDetail
}
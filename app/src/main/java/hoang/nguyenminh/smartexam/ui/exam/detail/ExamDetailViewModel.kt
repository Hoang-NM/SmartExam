package hoang.nguyenminh.smartexam.ui.exam.detail

import android.app.Application
import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.exam.GetExamDetailUseCase
import hoang.nguyenminh.smartexam.model.exam.ExamModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ExamDetailViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var useCase: GetExamDetailUseCase

    val flowOfDetail = MutableStateFlow<ExamModel?>(null)

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        args?.let {
            ExamDetailFragmentArgs.fromBundle(it)
        }?.let {
            flowOfDetail.value ?: execute(useCase, it.id, onSuccess = {
                flowOfDetail.value = it.toExamModel()
            })
        }
    }

    fun getDetail(): StateFlow<ExamModel?> = flowOfDetail
}
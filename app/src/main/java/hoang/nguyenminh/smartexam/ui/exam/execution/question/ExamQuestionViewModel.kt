package hoang.nguyenminh.smartexam.ui.exam.execution.question

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.base.scene.BaseAndroidViewModel
import javax.inject.Inject

@HiltViewModel
class ExamQuestionViewModel @Inject constructor(application: Application) :
    BaseAndroidViewModel(application)
package hoang.nguyenminh.smartexam.ui.exam.capture

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.base.scene.BaseAndroidViewModel
import javax.inject.Inject

@HiltViewModel
class ExamCaptureViewModel @Inject constructor(application: Application) :
    BaseAndroidViewModel(application)
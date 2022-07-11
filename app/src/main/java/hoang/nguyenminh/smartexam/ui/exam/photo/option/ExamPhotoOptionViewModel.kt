package hoang.nguyenminh.smartexam.ui.exam.photo.option

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import javax.inject.Inject

@HiltViewModel
class ExamPhotoOptionViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {
}
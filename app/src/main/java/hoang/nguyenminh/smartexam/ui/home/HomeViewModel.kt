package hoang.nguyenminh.smartexam.ui.home

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.base.scene.BaseAndroidViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) :
    BaseAndroidViewModel(application)
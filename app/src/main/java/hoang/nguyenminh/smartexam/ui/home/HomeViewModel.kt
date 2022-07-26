package hoang.nguyenminh.smartexam.ui.home

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.main.GetHomeInfoUseCase
import hoang.nguyenminh.smartexam.model.main.HomeInfo
import hoang.nguyenminh.smartexam.module.credential.CredentialManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var credentialManager: CredentialManager

    @Inject
    lateinit var useCase: GetHomeInfoUseCase

    val flowOfHomeContent = MutableStateFlow<HomeInfo?>(null)

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        val userId = credentialManager.getAuthenticationInfo()?.id ?: 0
        viewModelScope.launch {
            flowOfHomeContent.value = execute(useCase, userId)
        }
    }
}
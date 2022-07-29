package hoang.nguyenminh.smartexam.ui.profile

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.base.util.DateTimeXs.toUtcInstantString
import hoang.nguyenminh.base.util.postToBus
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.authentiaction.UpdateUserInfoUseCase
import hoang.nguyenminh.smartexam.model.UpdateUserInfoEvent
import hoang.nguyenminh.smartexam.model.apiTimeToLocalMillis
import hoang.nguyenminh.smartexam.model.authentication.UpdateUserInfoRequest
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.module.credential.CredentialManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(application: Application) :
    SmartExamViewModel(application) {

    @Inject
    lateinit var credentialManager: CredentialManager

    @Inject
    lateinit var useCase: UpdateUserInfoUseCase

    private val request by lazy {
        UpdateUserInfoRequest()
    }

    val flowOfUserInfo = MutableStateFlow<UserInfo?>(null)

    private val flowOfFirstName = MutableStateFlow("")

    private val flowOfLastName = MutableStateFlow("")

    private val flowOfAddress = MutableStateFlow("")

    private val flowOfClass = MutableStateFlow("")

    private val flowOfGender = MutableStateFlow(0)

    private val flowOfDateOfBirth = MutableStateFlow(0L)

    private val flowOfPhoneNumber = MutableStateFlow("")

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        flowOfUserInfo.value ?: run {
            flowOfUserInfo.value = credentialManager.getAuthenticationInfo()?.also {
                request.fromUserInfo(it)
                flowOfFirstName.value = it.firstName ?: ""
                flowOfLastName.value = it.lastName ?: ""
                flowOfAddress.value = it.address ?: ""
                flowOfGender.value = it.gender ?: 0
                flowOfDateOfBirth.value =
                    it.dob.apiTimeToLocalMillis() ?: System.currentTimeMillis()
                flowOfPhoneNumber.value = it.phoneNumber ?: ""
            }
        }
    }

    fun saveUserInfo() = viewModelScope.launch {
        request.apply {
            firstName = flowOfFirstName.value
            lastName = flowOfLastName.value
            address = flowOfAddress.value
            gender = flowOfGender.value
            dob = flowOfDateOfBirth.value.toUtcInstantString()
            phoneNumber = flowOfPhoneNumber.value
        }
        execute(useCase, request, onSuccess = {
            flowOfUserInfo.value?.let {
                credentialManager.saveAuthenticationInfo(it.fromUpdateRequest(request))
                UpdateUserInfoEvent(it).postToBus()
            }
        })
    }

    fun getFirstName() = flowOfFirstName

    fun getLastName() = flowOfLastName

    fun getAddress() = flowOfAddress

    fun getClassName() = flowOfClass

    fun getGender() = flowOfGender

    fun getDateOfBirth() = flowOfDateOfBirth

    fun getPhoneNumber() = flowOfPhoneNumber
}
package hoang.nguyenminh.smartexam.ui.profile

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hoang.nguyenminh.base.util.postToBus
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.interactor.authentiaction.UpdateUserInfoUseCase
import hoang.nguyenminh.smartexam.model.apiTimeToLocalMillis
import hoang.nguyenminh.smartexam.model.authentication.UpdateUserInfoRequest
import hoang.nguyenminh.smartexam.model.authentication.UserInfo
import hoang.nguyenminh.smartexam.model.toApiInstantString
import hoang.nguyenminh.smartexam.util.bus.UpdateUserInfoEvent
import hoang.nguyenminh.smartexam.util.module.credential.CredentialManager
import kotlinx.coroutines.flow.*
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

    private val flowOfUserInfo = MutableStateFlow<UserInfo?>(null)

    private val flowOfFirstName = MutableStateFlow("")

    private val flowOfLastName = MutableStateFlow("")

    private val flowOfAddress = MutableStateFlow("")

    private val flowOfClass = MutableStateFlow("")

    private val flowOfGender = MutableStateFlow(0)

    private val flowOfDateOfBirth = MutableStateFlow(0L)

    private val flowOfPhoneNumber = MutableStateFlow("")

    private val flowOfEnabled =
        combine(
            flow = flowOfFirstName,
            flow2 = flowOfLastName,
            flow3 = flowOfAddress,
            flow4 = flowOfPhoneNumber
        ) { firstName, lastName, address, phone ->
            firstName.isNotBlank() && lastName.isNotBlank() && address.isNotBlank() && phone.isNotBlank()
        }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    override fun onBind(args: Bundle?) {
        super.onBind(args)
        flowOfUserInfo.value ?: run {
            flowOfUserInfo.value = credentialManager.getAuthenticationInfo()?.also {
                flowOfFirstName.value = it.firstName ?: ""
                flowOfLastName.value = it.lastName ?: ""
                flowOfAddress.value = it.address ?: ""
                flowOfClass.value = it.className ?: ""
                flowOfGender.value = it.gender ?: 0
                flowOfDateOfBirth.value =
                    it.dob.apiTimeToLocalMillis() ?: System.currentTimeMillis()
                flowOfPhoneNumber.value = it.phoneNumber ?: ""
            }
        }
    }

    fun saveUserInfo() = viewModelScope.launch {
        request.apply {
            id = flowOfUserInfo.value?.id ?: 0
            firstName = flowOfFirstName.value
            lastName = flowOfLastName.value
            address = flowOfAddress.value
            gender = flowOfGender.value
            dob = flowOfDateOfBirth.value.toApiInstantString()
            phoneNumber = flowOfPhoneNumber.value
        }
        useCase(coroutineContext, request)
        flowOfUserInfo.value?.fromUpdateRequest(request)?.let {
            credentialManager.saveAuthenticationInfo(it)
            UpdateUserInfoEvent(it).postToBus()
        }
    }

    fun getFirstName(): MutableStateFlow<String> = flowOfFirstName

    fun getLastName(): MutableStateFlow<String> = flowOfLastName

    fun getAddress(): MutableStateFlow<String> = flowOfAddress

    fun getClassName(): MutableStateFlow<String> = flowOfClass

    fun getGender(): MutableStateFlow<Int> = flowOfGender

    fun getDateOfBirth(): MutableStateFlow<Long> = flowOfDateOfBirth

    fun getPhoneNumber(): MutableStateFlow<String> = flowOfPhoneNumber

    fun isEnabled(): StateFlow<Boolean> = flowOfEnabled
}
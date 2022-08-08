package hoang.nguyenminh.smartexam.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.ConfirmRequest
import hoang.nguyenminh.base.util.DateTimeXs
import hoang.nguyenminh.base.util.DateTimeXs.toTimeString
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentProfileBinding
import hoang.nguyenminh.smartexam.model.authentication.Gender
import hoang.nguyenminh.smartexam.model.generateMaxCurrentTimeRange
import hoang.nguyenminh.smartexam.util.showSingleDatePicker
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class ProfileFragment : SmartExamFragment<FragmentProfileBinding>() {

    override val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false).apply {
        viewModel.getGender().collectLatestOnLifecycle(viewLifecycleOwner) {
            if (it == Gender.MALE.value) viewGender.rbMale.isChecked = true
            else viewGender.rbFemale.isChecked = true
        }
        viewGender.group.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.rb_male -> viewModel.getGender().value = Gender.MALE.value
                R.id.rb_female -> viewModel.getGender().value = Gender.FEMALE.value
                else -> return@setOnCheckedChangeListener
            }
        }

        viewDateOfBirth.setOnClickInput {
            showSingleDatePicker(
                currentInLocal = viewModel.getDateOfBirth().value,
                constraints = generateMaxCurrentTimeRange()
            ) {
                viewModel.getDateOfBirth().value = it
            }
        }
        viewModel.getDateOfBirth().collectLatestOnLifecycle(viewLifecycleOwner) {
            viewDateOfBirth.content = it.toTimeString(DateTimeXs.FORMAT_DATE)
        }

        btnCancel.setOnSafeClickListener {
            requireActivity().onBackPressed()
        }
        btnSave.setOnSafeClickListener {
            showMessage(
                ConfirmRequest(
                    message = getString(R.string.message_confirm_update_user_info),
                    positive = getString(hoang.nguyenminh.base.R.string.common_ok),
                    onPositiveSelected = {
                        updateUserInfo()
                    }
                )
            )
        }
    }

    private fun updateUserInfo() = runBlocking {
        viewModel.saveUserInfo().join()
        showMessage(
            ConfirmRequest(
                message = getString(R.string.message_update_user_info_successfully),
                positive = getString(hoang.nguyenminh.base.R.string.common_ok),
                onPositiveSelected = {
                    requireActivity().onBackPressed()
                }
            )
        )
    }
}
package hoang.nguyenminh.smartexam.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.ConfirmRequest
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentProfileBinding
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class ProfileFragment : SmartExamFragment<FragmentProfileBinding>() {

    override val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false).apply {
        viewModel.flowOfUserInfo.collectLatestOnLifecycle(viewLifecycleOwner) {
            it ?: return@collectLatestOnLifecycle
            lblName.text = it.getName()
            lblEmail.text = it.email
            lblClass.text = it.className
        }
        btnCancel.setOnSafeClickListener {
            requireActivity().onBackPressed()
        }
        btnSave.setOnSafeClickListener {
            runBlocking {
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
    }
}
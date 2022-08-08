package hoang.nguyenminh.smartexam.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.ConfirmRequest
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentSettingBinding
import hoang.nguyenminh.smartexam.util.navigator.AppNavigator.toAuthentication
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class SettingFragment : SmartExamFragment<FragmentSettingBinding>() {

    override val viewModel: SettingViewModel by viewModels()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding = FragmentSettingBinding.inflate(inflater, container, false).apply {
        viewModel.flowOfUserInfo.collectLatestOnLifecycle(viewLifecycleOwner) {
            it ?: return@collectLatestOnLifecycle
            lblName.text = it.getName()
            lblEmail.text = it.email
            lblClass.text = it.className
        }

        lblProfile.setOnSafeClickListener {
            findNavController().navigate(NavigationMainDirections.toProfile())
        }

        lblLogout.setOnSafeClickListener {
            showMessage(
                ConfirmRequest(
                    message = getString(R.string.message_confirm_logout),
                    positive = getString(hoang.nguyenminh.base.R.string.confirm),
                    negative = getString(hoang.nguyenminh.base.R.string.cancel),
                    onPositiveSelected = {
                        runBlocking {
                            viewModel.clearAuthenticationInfo().join()
                            requireActivity().toAuthentication(true)
                        }
                    }
                )
            )
        }
    }
}
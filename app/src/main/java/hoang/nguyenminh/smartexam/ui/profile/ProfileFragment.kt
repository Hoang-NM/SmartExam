package hoang.nguyenminh.smartexam.ui.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.collectLatestOnLifecycle
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentProfileBinding
import hoang.nguyenminh.smartexam.ui.authentication.AuthenticationActivity
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

        btnLogout.setOnSafeClickListener {
            runBlocking {
                viewModel.clearAuthenticationInfo().join()
                val intent = Intent(requireContext(), AuthenticationActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }
}
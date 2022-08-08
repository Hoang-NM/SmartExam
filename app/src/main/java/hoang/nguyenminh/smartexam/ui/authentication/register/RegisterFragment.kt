package hoang.nguyenminh.smartexam.ui.authentication.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentRegisterBinding
import hoang.nguyenminh.smartexam.ui.authentication.login.LoginFragmentDirections
import hoang.nguyenminh.smartexam.util.navigator.AppNavigator.toMain

@AndroidEntryPoint
class RegisterFragment : SmartExamFragment<FragmentRegisterBinding>() {

    override val viewModel: RegisterViewModel by viewModels()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false).apply {
        btnRegister.setOnClickListener {
            requireActivity().toMain(true)
        }
        lblAlreadyHaveAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.toLogin())
        }
    }
}
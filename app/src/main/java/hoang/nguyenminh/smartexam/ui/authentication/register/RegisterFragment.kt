package hoang.nguyenminh.smartexam.ui.authentication.register

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentRegisterBinding
import hoang.nguyenminh.smartexam.ui.authentication.login.LoginFragmentDirections
import hoang.nguyenminh.smartexam.ui.main.MainActivity

@AndroidEntryPoint
class RegisterFragment : SmartExamFragment<FragmentRegisterBinding>() {

    override val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false).apply {
        binding = this
        btnRegister.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        lblAlreadyHaveAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.toLogin())
        }
    }
}
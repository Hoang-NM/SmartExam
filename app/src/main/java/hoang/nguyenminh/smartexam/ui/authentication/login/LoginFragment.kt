package hoang.nguyenminh.smartexam.ui.authentication.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentLoginBinding
import hoang.nguyenminh.smartexam.ui.main.MainActivity

@AndroidEntryPoint
class LoginFragment : SmartExamFragment<FragmentLoginBinding>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false).apply {
        btnLogin.setOnClickListener {
            viewModel.login {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        lblCreateAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.toRegister())
        }
    }
}
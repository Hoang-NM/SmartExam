package hoang.nguyenminh.smartexam.ui.authentication.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentLoginBinding
import hoang.nguyenminh.smartexam.navigator.AppNavigator.toMain
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class LoginFragment : SmartExamFragment<FragmentLoginBinding>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false).apply {
        btnLogin.setOnClickListener {
            runBlocking {
                viewModel.login()
                requireActivity().toMain(true)
            }
        }
        lblCreateAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.toRegister())
        }
    }
}
package hoang.nguyenminh.smartexam.ui.authentication.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.scene.BaseFragment
import hoang.nguyenminh.smartexam.BR
import hoang.nguyenminh.smartexam.databinding.FragmentLoginBinding
import hoang.nguyenminh.smartexam.ui.main.MainActivity

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    override val viewModel by viewModels<LoginViewModel>()

    override fun getViewModelVariableId(): Int = BR.vm

    private var binding: FragmentLoginBinding? = null

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): ViewDataBinding = FragmentLoginBinding.inflate(inflater, container, false).apply {
        binding = this
        btnLogin.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        lblCreateAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.toRegister())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
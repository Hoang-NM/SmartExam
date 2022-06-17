package hoang.nguyenminh.smartexam.ui.authentication.register

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.scene.BaseFragment
import hoang.nguyenminh.smartexam.BR
import hoang.nguyenminh.smartexam.databinding.FragmentRegisterBinding
import hoang.nguyenminh.smartexam.ui.authentication.login.LoginFragmentDirections
import hoang.nguyenminh.smartexam.ui.authentication.login.LoginViewModel
import hoang.nguyenminh.smartexam.ui.main.MainActivity

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {

    override val viewModel by viewModels<LoginViewModel>()

    override fun getViewModelVariableId(): Int = BR.vm

    private var binding: FragmentRegisterBinding? = null

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): ViewDataBinding = FragmentRegisterBinding.inflate(inflater, container, false).apply {
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
package hoang.nguyenminh.smartexam.ui.authentication.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.databinding.FragmentRegisterBinding
import hoang.nguyenminh.smartexam.ui.authentication.login.LoginFragmentDirections
import hoang.nguyenminh.smartexam.ui.authentication.login.LoginViewModel
import hoang.nguyenminh.smartexam.ui.main.MainActivity

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()

    private var binding: FragmentRegisterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentRegisterBinding.inflate(inflater, container, false).apply {
        binding = this
        btnRegister.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        lblAlreadyHaveAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.toLogin())
        }
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
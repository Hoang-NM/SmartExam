package hoang.nguyenminh.smartexam.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.databinding.FragmentLoginBinding
import hoang.nguyenminh.smartexam.ui.main.MainActivity

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel>()
    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(inflater, container, false).apply {
        binding = this
        btnLogin.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        lblCreateAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.toRegister())
        }
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
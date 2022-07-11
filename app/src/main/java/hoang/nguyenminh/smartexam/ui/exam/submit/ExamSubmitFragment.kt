package hoang.nguyenminh.smartexam.ui.exam.submit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamSubmitBinding

@AndroidEntryPoint
class ExamSubmitFragment : SmartExamFragment<FragmentExamSubmitBinding>() {

    override val viewModel: ExamSubmitViewModel by viewModels()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentExamSubmitBinding =
        FragmentExamSubmitBinding.inflate(inflater, container, false).apply {
            btnSubmit.setOnSafeClickListener {
                viewModel.submitExam()
                findNavController().navigate(NavigationMainDirections.popToExamMenu())
            }
        }
}
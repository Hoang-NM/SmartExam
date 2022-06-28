package hoang.nguyenminh.smartexam.ui.exam.display

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.base.SmartExamViewModel
import hoang.nguyenminh.smartexam.databinding.FragmentImageDisplayBinding


@AndroidEntryPoint
class ImageDisplayFragment : SmartExamFragment<FragmentImageDisplayBinding>() {

    override val viewModel: SmartExamViewModel by viewModels<ImageDisplayViewModel>()

    private val navArgs by navArgs<ImageDisplayFragmentArgs>()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentImageDisplayBinding =
        FragmentImageDisplayBinding.inflate(inflater, container, false).apply {
            binding = this
            image.setImageURI(Uri.parse(navArgs.path))
        }
}
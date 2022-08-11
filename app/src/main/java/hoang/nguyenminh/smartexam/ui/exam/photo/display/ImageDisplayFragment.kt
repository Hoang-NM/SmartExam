package hoang.nguyenminh.smartexam.ui.exam.photo.display

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.ConfirmRequest
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentImageDisplayBinding
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class ImageDisplayFragment : SmartExamFragment<FragmentImageDisplayBinding>() {

    override val viewModel: ImageDisplayViewModel by viewModels()

    private val navArgs by navArgs<ImageDisplayFragmentArgs>()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentImageDisplayBinding =
        FragmentImageDisplayBinding.inflate(inflater, container, false).apply {
            navArgs.uri.let {
                image.setImageURI(navArgs.uri)
            }
            btnSend.setOnSafeClickListener {
                runBlocking {
                    viewModel.sendExamImage {
                        showMessage(
                            ConfirmRequest(
                                message = getString(R.string.message_submit_exam_image_successfully),
                                positive = getString(hoang.nguyenminh.base.R.string.common_ok),
                                onPositiveSelected = {
                                    findNavController().navigate(NavigationMainDirections.popToExamMenu())
                                }
                            )
                        )
                    }
                }
            }
        }
}
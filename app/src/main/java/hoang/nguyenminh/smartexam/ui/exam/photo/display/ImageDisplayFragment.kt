package hoang.nguyenminh.smartexam.ui.exam.photo.display

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.ConfirmRequest
import hoang.nguyenminh.base.util.postImageRotate
import hoang.nguyenminh.base.util.rotate
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
            navArgs.path.let {
                val matrix = Matrix().apply { postImageRotate(it) }
                val bitmap = BitmapFactory.decodeFile(it)
                image.setImageBitmap(bitmap.rotate(matrix))
            }
            btnSend.setOnSafeClickListener {
                runBlocking {
                    viewModel.sendExamImage().join()
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
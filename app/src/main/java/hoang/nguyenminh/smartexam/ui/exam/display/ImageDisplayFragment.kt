package hoang.nguyenminh.smartexam.ui.exam.display

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.postImageRotate
import hoang.nguyenminh.base.util.rotate
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentImageDisplayBinding


@AndroidEntryPoint
class ImageDisplayFragment : SmartExamFragment<FragmentImageDisplayBinding>() {

    override val viewModel: ImageDisplayViewModel by viewModels()

    private val navArgs by navArgs<ImageDisplayFragmentArgs>()

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentImageDisplayBinding =
        FragmentImageDisplayBinding.inflate(inflater, container, false).apply {
            binding = this
            navArgs.path.let {
                val matrix = Matrix().apply { postImageRotate(it) }
                val bitmap = BitmapFactory.decodeFile(it)
                image.setImageBitmap(bitmap.rotate(matrix))
            }
            btnSend.setOnSafeClickListener {
                viewModel.sendExamImage(navArgs.path)
                findNavController().navigate(ImageDisplayFragmentDirections.popToExamMenu())
            }
        }
}
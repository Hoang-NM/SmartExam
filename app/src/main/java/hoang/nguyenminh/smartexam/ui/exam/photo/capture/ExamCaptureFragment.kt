package hoang.nguyenminh.smartexam.ui.exam.photo.capture

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.util.FileXs
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamCaptureBinding
import okio.buffer
import okio.sink
import timber.log.Timber

@AndroidEntryPoint
class ExamCaptureFragment : SmartExamFragment<FragmentExamCaptureBinding>() {

    override val viewModel: ExamCaptureViewModel by viewModels()

    private val imageCapture by lazy {
        ImageCapture.Builder().build()
    }

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentExamCaptureBinding =
        FragmentExamCaptureBinding.inflate(inflater, container, false).apply {
            btnCapture.setOnClickListener {
                capture(requireContext())
            }
            startCamera()
        }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding?.preview?.surfaceProvider)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Timber.e("Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun capture(context: Context) {
        imageCapture.takePicture(ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    findNavController().navigate(
                        NavigationMainDirections.toImageDisplay(createTempImagePath(image))
                    )
                }
            })
    }

    private fun createTempImagePath(image: ImageProxy): String {
        val tempFile = FileXs.createTempFile(
            requireContext(), "smart-exam-${System.currentTimeMillis()}", ".jpeg"
        )
        val source = image.planes[0].buffer
        tempFile.sink().buffer().apply {
            write(source)
            close()
        }
        return tempFile.absolutePath
    }
}
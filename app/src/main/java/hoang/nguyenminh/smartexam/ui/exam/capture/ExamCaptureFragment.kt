package hoang.nguyenminh.smartexam.ui.exam.capture

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.R
import hoang.nguyenminh.base.util.ConfirmRequest
import hoang.nguyenminh.base.util.FileXs
import hoang.nguyenminh.base.util.buildAlertDialog
import hoang.nguyenminh.base.util.isAllPermissionsGranted
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamCaptureBinding
import okio.buffer
import okio.sink
import timber.log.Timber

@AndroidEntryPoint
class ExamCaptureFragment : SmartExamFragment<FragmentExamCaptureBinding>() {

    override val viewModel: ExamCaptureViewModel by viewModels()

    private var permissionLauncher: ActivityResultLauncher<Array<String>>? = null

    private val imageCapture by lazy {
        ImageCapture.Builder().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                if (it.all { true }) {
                    startCamera()
                } else {
                    ConfirmRequest(message = "Please allow application access the required permissions to continue",
                        positive = getString(R.string.agree),
                        onPositiveSelected = {
                            requestPermission()
                        },
                        negative = getString(R.string.cancel),
                        onNegativeSelected = {
                            requireActivity().onBackPressed()
                        }).buildAlertDialog(requireContext())
                }
            }
    }

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentExamCaptureBinding =
        FragmentExamCaptureBinding.inflate(inflater, container, false).apply {
            binding = this

            if (requireContext().isAllPermissionsGranted(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                startCamera()
            } else {
                requestPermission()
            }

            btnCapture.setOnClickListener {
                capture(requireContext())
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun requestPermission() {
        permissionLauncher?.launch(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        )
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
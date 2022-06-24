package hoang.nguyenminh.smartexam.ui.exam.capture

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.R
import hoang.nguyenminh.base.util.ConfirmRequest
import hoang.nguyenminh.base.util.DateTimeXs
import hoang.nguyenminh.base.util.buildAlertDialog
import hoang.nguyenminh.base.util.isAllPermissionsGranted
import hoang.nguyenminh.smartexam.base.SmartExamFragment
import hoang.nguyenminh.smartexam.databinding.FragmentExamCaptureBinding
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ExamCaptureFragment : SmartExamFragment<FragmentExamCaptureBinding>() {

    override val viewModel by viewModels<ExamCaptureViewModel>()

    private var storagePermissionLauncher: ActivityResultLauncher<String>? = null
    private var cameraPermissionLauncher: ActivityResultLauncher<String>? = null

    private val imageCapture by lazy {
        ImageCapture.Builder().build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storagePermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    return@registerForActivityResult
                } else {
                    ConfirmRequest(message = "Please allow application access the external storage to continue",
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
        cameraPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    startCamera()
                } else {
                    ConfirmRequest(message = "Please allow application access the camera to continue",
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
        storagePermissionLauncher?.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        cameraPermissionLauncher?.launch(Manifest.permission.CAMERA)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding?.preview?.surfaceProvider)
            }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Timber.e("Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun capture(context: Context) {
        // Create time stamped name and MediaStore entry.
        val name =
            SimpleDateFormat(DateTimeXs.FORMAT_DATE, Locale.US).format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            context.contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
        ).build()

        // Set up image capture listener, which is triggered after photo has been taken
        imageCapture.takePicture(outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Timber.e("Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    Timber.d(msg)
                }
            })
    }
}
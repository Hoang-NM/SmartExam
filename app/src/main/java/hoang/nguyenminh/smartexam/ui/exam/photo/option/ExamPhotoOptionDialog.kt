package hoang.nguyenminh.smartexam.ui.exam.photo.option

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import hoang.nguyenminh.base.R
import hoang.nguyenminh.base.util.ConfirmRequest
import hoang.nguyenminh.base.util.isAllPermissionsGranted
import hoang.nguyenminh.base.util.setOnSafeClickListener
import hoang.nguyenminh.smartexam.NavigationMainDirections
import hoang.nguyenminh.smartexam.base.SmartExamBottomSheetDialog
import hoang.nguyenminh.smartexam.databinding.DialogPhotoOptionBinding


@AndroidEntryPoint
class ExamPhotoOptionDialog : SmartExamBottomSheetDialog<DialogPhotoOptionBinding>() {

    override val viewModel: ExamPhotoOptionViewModel by viewModels()

    private val args by navArgs<ExamPhotoOptionDialogArgs>()

    private var cameraPermissionLauncher: ActivityResultLauncher<String>? = null

    private var storagePermissionLauncher: ActivityResultLauncher<String>? = null

    val PICK_PHOTO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    binding?.tvCapture?.callOnClick()
                } else {
                    showMessage(
                        ConfirmRequest(
                            message = "Please allow application to access the camera to continue",
                            positive = getString(R.string.confirm),
                            negative = getString(R.string.cancel),
                            onPositiveSelected = { requestCameraPermission() },
                            onNegativeSelected = { requireActivity().onBackPressed() })
                    )
                }
            }

        storagePermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    binding?.tvPhotoPicker?.callOnClick()
                } else {
                    showMessage(
                        ConfirmRequest(
                            message = "Please allow application access the external storage to continue",
                            positive = getString(R.string.confirm),
                            negative = getString(R.string.cancel),
                            onPositiveSelected = { requestStoragePermission() },
                            onNegativeSelected = { requireActivity().onBackPressed() })
                    )
                }
            }
    }

    override fun onCreateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogPhotoOptionBinding =
        DialogPhotoOptionBinding.inflate(inflater, container, false).apply {
            tvCapture.setOnSafeClickListener {
                if (requireContext().isAllPermissionsGranted(Manifest.permission.CAMERA)) {
                    findNavController().navigate(NavigationMainDirections.toExamCapture(args.examId))
                } else {
                    requestCameraPermission()
                }
            }

            @Suppress("DEPRECATION")
            tvPhotoPicker.setOnSafeClickListener {
                if (requireContext().isAllPermissionsGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"
                    startActivityForResult(Intent.createChooser(intent, "Pick Photo"), PICK_PHOTO)
                } else {
                    requestStoragePermission()
                }
            }

            tvCancel.setOnSafeClickListener {
                this@ExamPhotoOptionDialog.dismiss()
            }
        }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                findNavController().navigate(
                    NavigationMainDirections.toImageDisplay(args.examId, uri)
                )
            }
        }
    }

    private fun requestCameraPermission() {
        cameraPermissionLauncher?.launch(Manifest.permission.CAMERA)
    }

    private fun requestStoragePermission() {
        storagePermissionLauncher?.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}
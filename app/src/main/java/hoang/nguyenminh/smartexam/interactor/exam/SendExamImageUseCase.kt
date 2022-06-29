package hoang.nguyenminh.smartexam.interactor.exam

import hoang.nguyenminh.base.usecase.CoroutinesUseCase
import hoang.nguyenminh.base.util.IMAGE_SIZE_LIMIT
import hoang.nguyenminh.base.util.getMimeType
import hoang.nguyenminh.base.util.prepareImageFileForUpload
import hoang.nguyenminh.smartexam.model.exam.SubmitExamImageRequest
import hoang.nguyenminh.smartexam.repository.cloud.SmartExamCloudRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class SendExamImageUseCase @Inject constructor(private val repository: SmartExamCloudRepository) :
    CoroutinesUseCase<Unit, SubmitExamImageRequest>() {

    override suspend fun run(params: SubmitExamImageRequest) {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        params.path.let {
            val file = prepareImageFileForUpload(File(it), IMAGE_SIZE_LIMIT)
            val body = file.asRequestBody(file.getMimeType("image/jpeg").toMediaTypeOrNull())
            builder.addFormDataPart("images", file.name, body)
        }
        val requestBody = builder.build()
        repository.sendExamImage(requestBody)
    }
}
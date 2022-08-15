package hoang.nguyenminh.base.util

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Base64
import androidx.camera.core.ImageProxy
import androidx.exifinterface.media.ExifInterface
import hoang.nguyenminh.base.appInstance
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.ByteBuffer

const val IMAGE_SIZE_LIMIT: Long = 1024 * 1024 // 1MB

fun Bitmap.encodeBase64(): String {
    val outputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

@SuppressLint("UnsafeOptInUsageError")
fun ImageProxy.toBitmap(): Bitmap? {
    return image?.let {
        val buffer: ByteBuffer = it.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null)
    }
}

fun ImageProxy.encodeBase64(): String? {
    val outputStream = ByteArrayOutputStream()
    val bitmap = toBitmap() ?: return null
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun prepareImageFileForUpload(file: File, limitSizeInByte: Long): File {
    if (!file.exists()) return file
    Timber.d("resize image input -> ${file.length() / 1024}kB @${file.absolutePath}")
    val outputFile =
        FileXs.createTempFile(appInstance(), "temp_${System.currentTimeMillis()}", ".jpeg")
    val quality = if (file.length() >= limitSizeInByte) 90 else 100
    standardizeImageFileOrientation(file, outputFile, quality)
    if (outputFile.length() >= limitSizeInByte) {
        recursiveReduceImageFileSize(outputFile, limitSizeInByte)
    }
    Timber.d("resizeBitmap output -> ${outputFile.length() / 1024}kB @${outputFile.absolutePath}")
    return outputFile
}

private tailrec fun recursiveReduceImageFileSize(file: File, limitSizeInByte: Long) {
    val options = BitmapFactory.Options().apply {
        inSampleSize = 2
    }
    val inputStream = FileInputStream(file)
    val bitmap = BitmapFactory.decodeStream(inputStream, null, options) ?: return
    inputStream.close()
    val outputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    bitmap.recycle()
    outputStream.close()
    Timber.d("recursiveCompressImage ${file.length() / 1024}kB")
    if (file.length() > limitSizeInByte) {
        recursiveReduceImageFileSize(file, limitSizeInByte)
    }
}

fun standardizeImageFileOrientation(input: File, output: File, quality: Int = 100) {
    if (!input.exists()) return
    val matrix = Matrix().apply { postImageRotate(input.absolutePath) }
    val original = BitmapFactory.decodeFile(input.absolutePath)
    val rotated = original.rotate(matrix)
    val fos = FileOutputStream(output)
    rotated.compress(Bitmap.CompressFormat.JPEG, quality, fos)
    fos.close()
    original.recycle()
    rotated.recycle()
}

fun Matrix.postImageRotate(path: String) {
    when (ExifInterface(path).getAttributeInt(
        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
    )) {
        ExifInterface.ORIENTATION_NORMAL -> postRotate(0F)
        ExifInterface.ORIENTATION_ROTATE_90 -> postRotate(90F)
        ExifInterface.ORIENTATION_ROTATE_180 -> postRotate(180F)
        ExifInterface.ORIENTATION_ROTATE_270 -> postRotate(270F)
    }
}

fun Bitmap.rotate(matrix: Matrix): Bitmap =
    Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
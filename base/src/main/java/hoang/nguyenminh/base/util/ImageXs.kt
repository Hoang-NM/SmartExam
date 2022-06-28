package hoang.nguyenminh.base.util

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.camera.core.ImageProxy
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer


object ImageXs {

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

    @SuppressLint("UnsafeOptInUsageError")
    fun ImageProxy.encodeBase64(): String? {
        val outputStream = ByteArrayOutputStream()
        val bitmap = toBitmap() ?: return null
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}
package hoang.nguyenminh.base.util

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import hoang.nguyenminh.base.appInstance
import java.io.*

object FileXs {

    fun createTempFile(context: Context, name: String, suffix: String): File {
        val storageDir: File? = context.getExternalFilesDir("")
        return File.createTempFile(name, suffix, storageDir)
    }

    fun clearTempFiles(context: Context, filter: FileFilter = FileFilter { true }) {
        context.getExternalFilesDir("")?.listFiles(filter)?.forEach {
            it.deleteRecursively()
        }
    }
}

fun File.getMimeType(fallback: String = "image/*"): String =
    MimeTypeMap.getFileExtensionFromUrl(toString())?.run {
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(lowercase())
    } ?: fallback

fun fileFromContentUri(contentUri: Uri): File {
    val context = appInstance()
    val fileExtension = getFileExtension(context, contentUri)
    val tempFile =
        FileXs.createTempFile(context, "temp-${System.currentTimeMillis()}", fileExtension ?: "")

    try {
        val outputStream = FileOutputStream(tempFile)
        val inputStream = context.contentResolver.openInputStream(contentUri)

        inputStream?.let {
            copy(inputStream, outputStream)
        }

        outputStream.flush()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return tempFile
}

private fun getFileExtension(context: Context, uri: Uri): String? {
    val fileType: String? = context.contentResolver.getType(uri)
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
}

@Throws(IOException::class)
private fun copy(source: InputStream, target: OutputStream) {
    val buf = ByteArray(8192)
    var length: Int
    while (source.read(buf).also { length = it } > 0) {
        target.write(buf, 0, length)
    }
}
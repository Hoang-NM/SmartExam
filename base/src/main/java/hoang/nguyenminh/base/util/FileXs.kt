package hoang.nguyenminh.base.util

import android.content.Context
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileFilter

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
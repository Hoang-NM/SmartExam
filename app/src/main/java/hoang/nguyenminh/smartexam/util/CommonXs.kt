package hoang.nguyenminh.smartexam.util

import android.content.Context
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import hoang.nguyenminh.smartexam.appInstance
import java.io.Serializable

data class ConfirmRequest(
    val icon: Int? = null,
    val title: String? = null,
    val message: String? = null,
    val positive: String? = null,
    val negative: String? = null,
    val neutral: String? = null,
    val cancelable: Boolean = true,
    @Transient val onPositiveSelected: (() -> Unit)? = null,
    @Transient val onNegativeSelected: (() -> Unit)? = null,
    @Transient val onNeutralSelected: (() -> Unit)? = null,
) : Serializable

fun ConfirmRequest.buildAlertDialog(context: Context, @StyleRes style: Int = 0): AlertDialog =
    AlertDialog.Builder(context, style)
        .apply {
            setTitle(title)
                .setMessage(message)
                .setCancelable(cancelable)
                .setIcon(icon?.let {
                    if (it == 0) null else ResourcesCompat.getDrawable(
                        appInstance().resources, it, appInstance().theme
                    )
                })
            positive?.let {
                setPositiveButton(it) { dialog, _ ->
                    dialog.dismiss()
                    onPositiveSelected?.invoke()
                }
            }
            negative?.let {
                setNegativeButton(it) { dialog, _ ->
                    dialog.dismiss()
                    onNegativeSelected?.invoke()
                }
            }
            neutral?.let {
                setNeutralButton(it) { dialog, _ ->
                    dialog.dismiss()
                    onNeutralSelected?.invoke()
                }
            }
        }.create()
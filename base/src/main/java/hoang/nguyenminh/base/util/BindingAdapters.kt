package hoang.nguyenminh.base.util

import android.text.InputType
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import hoang.nguyenminh.base.util.DateTimeXs.FORMAT_DATE
import hoang.nguyenminh.base.util.DateTimeXs.FORMAT_DATE_TIME_ISO
import java.text.SimpleDateFormat
import java.util.*


object BindingAdapters {

    @BindingAdapter("imageUrl")
    fun ImageView.loadImage(url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(context).load(url).into(this)
        }
    }

    @BindingAdapter("dateFormatter")
    fun TextView.dateFormatter(string: String?) {
        if (string?.isNotEmpty() == true) {
            val date = SimpleDateFormat(FORMAT_DATE_TIME_ISO, Locale.getDefault()).parse(string)
            date?.let {
                val format = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
                this.text = format.format(it)
            }
        }
    }

    @BindingAdapter("viewCompatEnabled")
    @JvmStatic
    fun View.viewCompatEnabled(enabled: Boolean) {
        isEnabled = enabled
        isClickable = enabled
    }

    @BindingAdapter("viewCompatVisibility")
    @JvmStatic
    fun View.viewCompatVisibility(visible: Boolean) {
        visibility = if (visible) View.VISIBLE else View.GONE
    }

    @BindingAdapter("viewCompatSelected")
    @JvmStatic
    fun View.viewCompatSelected(selected: Boolean) {
        isSelected = selected
    }

    @BindingAdapter("bindInputType")
    @JvmStatic
    fun AppCompatEditText.bindInputType(type: Int?) {
        inputType = type ?: InputType.TYPE_CLASS_TEXT
    }
}
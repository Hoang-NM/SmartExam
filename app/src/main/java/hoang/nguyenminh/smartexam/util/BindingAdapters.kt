package hoang.nguyenminh.smartexam.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    const val FORMAT_DATE = "dd/MM/yyy"
    const val FORMAT_DATE_TIME_ISO = "yyyy-mm-dd'T'HH:mm:ss'Z'"

    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(view.context).load(url).into(view)
        }
    }

    @BindingAdapter("dateFormatter")
    fun TextView.dateFormatter(string: String?) {
        if (string?.isNotEmpty() == true) {
            val date =
                SimpleDateFormat(FORMAT_DATE_TIME_ISO, Locale.getDefault()).parse(string)
            date?.let {
                val format = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
                this.text = format.format(it)
            }
        }
    }
}
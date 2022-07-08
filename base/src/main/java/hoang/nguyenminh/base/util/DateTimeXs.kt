package hoang.nguyenminh.base.util

import java.text.SimpleDateFormat
import java.util.*

object DateTimeXs {
    const val FORMAT_DATE = "dd/MM/yyy"
    const val FORMAT_DATE_TIME_ISO = "yyyy-mm-dd'T'HH:mm:ss'Z'"
    const val FORMAT_COUNT_DOWN_TIME = "mm:ss"

    const val SECOND: Long = 1000
    const val MINUTE = SECOND * 60
    const val HOUR = MINUTE * 60
    const val DAY = HOUR * 24
    const val WEEK = DAY * 7
    const val YEAR = DAY * 365

    fun getSimpleDateFormat(pattern: String) = SimpleDateFormat(pattern, Locale.getDefault())

    fun Long.toTimeString(pattern: String): String =
        if (this <= 0L) ""
        else getSimpleDateFormat(pattern).format(Date(this))
}
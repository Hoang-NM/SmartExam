package hoang.nguyenminh.base.util

import android.text.TextUtils
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeXs {
    const val FORMAT_DATE = "dd/MM/yyyy"
    const val FORMAT_DATE_TIME_ISO = "yyyy-mm-dd'T'HH:mm:ss'Z'"
    const val FORMAT_COUNT_DOWN_TIME = "mm:ss"
    const val FORMAT_DATE_STUPID = "dd-MM-yy"

    const val SECOND: Long = 1000
    const val MINUTE = SECOND * 60
    const val HOUR = MINUTE * 60
    const val DAY = HOUR * 24
    const val WEEK = DAY * 7
    const val YEAR = DAY * 365

    const val TIMEZONE_UTC = "UTC"

    val TIMEZONE_UTC_INSTANT: TimeZone = TimeZone.getTimeZone(TIMEZONE_UTC)

    fun getSimpleDateFormat(pattern: String, timeZone: String?) =
        SimpleDateFormat(pattern, Locale.getDefault()).apply {
            if (timeZone.isNullOrEmpty()) {
                return@apply
            }
            this.timeZone = TimeZone.getTimeZone(timeZone)
        }

    fun Long.toTimeString(pattern: String, timeZone: String? = null): String =
        if (this <= 0L) ""
        else getSimpleDateFormat(pattern, timeZone).format(Date(this))

    fun Long.toUtcInstantString(): String {
        return SimpleDateFormat(FORMAT_DATE_TIME_ISO, Locale.US)
            .apply {
                timeZone = TIMEZONE_UTC_INSTANT
            }.format(Date(this))
    }

    fun String.toDate(pattern: String, timeZone: String? = null): Date? {
        return if (TextUtils.isEmpty(this)) {
            null
        } else try {
            getSimpleDateFormat(pattern, timeZone).parse(this)
        } catch (e: ParseException) {
            Timber.e("parse date exception $pattern -> $this")
            null
        }
    }
}
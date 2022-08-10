package hoang.nguyenminh.smartexam.model

import hoang.nguyenminh.base.util.DateTimeXs
import hoang.nguyenminh.base.util.DateTimeXs.toDate
import java.text.SimpleDateFormat
import java.util.*

object AppConst {
    const val BASE_URL = "https://6df5-58-187-94-173.ap.ngrok.io/api/"
    const val FORMAT_DATE_STUPID = "dd-MM-yy"
}

object AppNavigator {
    const val MENU_EXAM_EXECUTION = "exam/execution"
    const val MENU_EXAM_HISTORY = "exam/history"
}

const val API_PATTERN_INSTANT = AppConst.FORMAT_DATE_STUPID

internal const val POST_FIX_TIMEZONE_UTC = 'Z'

const val OUT_PATTERN_SHORT_DATE = "dd/MM/yyyy"

fun String?.apiTimeToLocalMillis(pattern: String = API_PATTERN_INSTANT): Long? = when {
    isNullOrEmpty() -> null
    endsWith(POST_FIX_TIMEZONE_UTC, true) -> toDate(pattern, DateTimeXs.TIMEZONE_UTC)?.time ?: 0L
    else -> toDate(pattern)?.time ?: 0L
}

fun Long.toApiInstantString(): String {
    return SimpleDateFormat(API_PATTERN_INSTANT, Locale.US)
        .apply {
            timeZone = DateTimeXs.TIMEZONE_UTC_INSTANT
        }.format(Date(this))
}

fun generateMaxCurrentTimeRange(): LongRange =
    0..System.currentTimeMillis()
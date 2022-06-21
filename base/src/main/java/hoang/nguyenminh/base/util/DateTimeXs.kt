package hoang.nguyenminh.base.util

object DateTimeXs {
    const val FORMAT_DATE = "dd/MM/yyy"
    const val FORMAT_DATE_TIME_ISO = "yyyy-mm-dd'T'HH:mm:ss'Z'"

    const val SECOND: Long = 1000
    const val MINUTE = SECOND * 60
    const val HOUR = MINUTE * 60
    const val DAY = HOUR * 24
    const val WEEK = DAY * 7
    const val YEAR = DAY * 365
}
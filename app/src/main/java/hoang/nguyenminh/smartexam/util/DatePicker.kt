package hoang.nguyenminh.smartexam.util

import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import hoang.nguyenminh.smartexam.R
import timber.log.Timber
import java.util.*

fun Fragment.showSingleDatePicker(
    currentInLocal: Long,
    constraints: LongRange? = null,
    callback: (Long) -> Unit,
) {
    val utcCompensation = Calendar.getInstance().get(Calendar.ZONE_OFFSET)
    val builder: MaterialDatePicker.Builder<Long> = MaterialDatePicker.Builder
        .datePicker()
        .setTheme(com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialCalendar)
        .setTitleText(R.string.title_date_picker)
        .setSelection(currentInLocal + utcCompensation)
    val constraintsBuilder = CalendarConstraints.Builder()
    constraints?.let {
        if (it.first > 0) {
            constraintsBuilder.setStart(it.first)
        }
        if (it.last < Long.MAX_VALUE) {
            constraintsBuilder.setEnd(it.last)
        }
        constraintsBuilder.setValidator(DateInRangeValidator(it))
    }
    try {
        builder.setCalendarConstraints(
            constraintsBuilder
                .build()
        )
        builder.build().apply {
            addOnPositiveButtonClickListener { nullableMs ->
                nullableMs?.let { ms ->
                    callback(ms - utcCompensation)
                }
            }
            show(
                this@showSingleDatePicker.childFragmentManager,
                MaterialDatePicker::class.java.simpleName
            )
        }
    } catch (e: IllegalArgumentException) {
        Timber.e(e)
        context?.let {
            Toast.makeText(it, R.string.message_unhandled_error, Toast.LENGTH_SHORT).show()
        }
    }
}

data class DateInRangeValidator(private val range: LongRange) : CalendarConstraints.DateValidator {

    constructor(parcel: Parcel) : this(parcel.readLong()..parcel.readLong())

    override fun isValid(date: Long): Boolean = date in range

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(range.first)
        parcel.writeLong(range.last)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<DateInRangeValidator> {
        override fun createFromParcel(parcel: Parcel): DateInRangeValidator {
            return DateInRangeValidator(parcel)
        }

        override fun newArray(size: Int): Array<DateInRangeValidator?> {
            return arrayOfNulls(size)
        }
    }

}
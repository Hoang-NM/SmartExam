package hoang.nguyenminh.base.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.FloatRange
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

fun BottomSheetDialogFragment.setupPercentage(
    @FloatRange(from = 0.0, to = 1.0) heightPercentage: Float
) {
    dialog?.apply {
        val bottomSheet = findViewById<View>(R.id.design_bottom_sheet)
        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        bottomSheet.background = ColorDrawable(Color.TRANSPARENT)
    }
    view?.run {
        post {
            (dialog as? BottomSheetDialog)?.behavior?.let {
                val parent = parent as View
                val params = parent.layoutParams as ViewGroup.LayoutParams
                val peekHeight = (measuredHeight * heightPercentage).toInt()
                it.peekHeight = peekHeight
                params.height = peekHeight
            }
        }
    }
}
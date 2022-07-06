package hoang.nguyenminh.smartexam.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Px
import hoang.nguyenminh.smartexam.R
import hoang.nguyenminh.base.R as baseR

class CornerFocusZoneView : View {

    private val paint = Paint()

    private val topStartRect = Rect()
    private val topEndRect = Rect()
    private val bottomStartRect = Rect()
    private val bottomEndRect = Rect()

    @Px
    private var rectWidth = 0

    @Px
    private var rectHeight = 0

    @Px
    private var cornerPadding = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    @Suppress("unused")
    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        paint.color = Color.WHITE
        paint.strokeWidth = resources.getDimensionPixelSize(baseR.dimen.space_small).toFloat()
        paint.style = Paint.Style.STROKE

        rectWidth = resources.getDimensionPixelSize(baseR.dimen.space_xlarge)
        rectHeight = resources.getDimensionPixelSize(baseR.dimen.space_xlarge)
        cornerPadding = 0
    }

    @Suppress("DEPRECATION")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = measuredWidth
        val height = measuredHeight

        topStartRect.apply {
            left = cornerPadding
            top = cornerPadding
            right = rectWidth + cornerPadding
            bottom = rectHeight + cornerPadding
        }

        topEndRect.apply {
            left = width - cornerPadding
            top = cornerPadding
            right = width - rectWidth - cornerPadding
            bottom = rectHeight + cornerPadding
        }

        bottomStartRect.apply {
            left = cornerPadding
            top = height / 2 - rectHeight - cornerPadding
            right = rectWidth + cornerPadding
            bottom = height / 2 - cornerPadding
        }

        bottomEndRect.apply {
            left = width - cornerPadding
            top = height / 2 - rectHeight - cornerPadding
            right = width - rectWidth - cornerPadding
            bottom = height / 2 - cornerPadding
        }

        canvas.drawRect(topStartRect, paint)
        canvas.drawRect(topEndRect, paint)
        canvas.drawRect(bottomStartRect, paint)
        canvas.drawRect(bottomEndRect, paint)

        canvas.drawColor(resources.getColor(R.color.color_background_black))
    }
}
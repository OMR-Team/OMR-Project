package com.lok.dev.omrchecker.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.FloatRange
import com.lok.dev.commonutil.color
import com.lok.dev.commonutil.toDp
import com.lok.dev.omrchecker.R

class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.color(R.color.theme_blue_primary)
        strokeWidth = 100.toDp(context)
        style = Paint.Style.STROKE
    }
    private val backgroundPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.color(R.color.theme_black_6)
        strokeWidth = progressPaint.strokeWidth
        style = Paint.Style.STROKE
    }

    private val rect = RectF()
    private val startAngle = -90f
    private val maxAngle = 360f
    private val maxProgress = 100

    private var diameter = 0f
    private var angle = 0f

    override fun onDraw(canvas: Canvas) {
        drawCircle(maxAngle, canvas, backgroundPaint)
        drawCircle(angle, canvas, progressPaint)
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        diameter = width.coerceAtMost(height).toFloat()
        updateRect()
    }

    private fun updateRect() {
        val strokeWidth = backgroundPaint.strokeWidth
        progressPaint.strokeCap = Paint.Cap.ROUND
        rect.set(strokeWidth, strokeWidth, diameter - strokeWidth, diameter - strokeWidth)
    }

    private fun drawCircle(angle: Float, canvas: Canvas, paint: Paint) {
        canvas.drawArc(rect, startAngle, angle, false, paint)
    }

    private fun calculateAngle(progress: Float) = maxAngle / maxProgress * progress

    private var progress = 0f
    private fun setProgress(@FloatRange(from = 0.0, to = 100.0) progress: Float) {
        angle = calculateAngle(progress)
        this.progress = progress
        invalidate()
    }

    private var progressAnimator: ValueAnimator? = null

    fun setProgressAnimated(
        newProgress: Float,
        maxDurationMillis: Long = 500L
    ) {
        progressAnimator?.cancel()
        progress = 0f
        progressAnimator = ValueAnimator.ofFloat(0f, newProgress).apply {
            duration = maxDurationMillis
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                setProgress(it.animatedValue as Float)
            }
            start()
        }
    }

}
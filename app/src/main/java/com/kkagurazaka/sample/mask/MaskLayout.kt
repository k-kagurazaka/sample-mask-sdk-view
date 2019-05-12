package com.kkagurazaka.sample.mask

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout

class MaskLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var canvasBitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private var maskPaint: Paint? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w == 0 || h == 0) {
            canvasBitmap = null
            canvas = null
            maskPaint = null
            return
        }

        if (w != oldw || h != oldh) {
            canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
                    .also { canvas = Canvas(it) }

            maskPaint = Paint().apply {
                shader = LinearGradient(
                        0f, 0f, 0f, h.toFloat(),
                        intArrayOf(Color.TRANSPARENT, Color.WHITE, Color.WHITE, Color.TRANSPARENT),
                        floatArrayOf(0f, 0.15f, 0.85f, 1f),
                        Shader.TileMode.CLAMP
                )
                xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            }
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        val offScreenBitmap = canvasBitmap ?: return super.dispatchDraw(canvas)
        val offScreenBuffer = this.canvas ?: return super.dispatchDraw(canvas)
        val mask = maskPaint ?: return super.dispatchDraw(canvas)

        // clear off-screen buffer
        offScreenBuffer.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        // render child views to off-screen buffer
        super.dispatchDraw(offScreenBuffer)

        // apply gradient mask into off-screen buffer
        offScreenBuffer.drawRect(
                0f,
                0f,
                offScreenBitmap.width.toFloat(),
                offScreenBitmap.height.toFloat(),
                mask
        )

        // write off-screen buffer image to actual canvas
        canvas.drawBitmap(offScreenBitmap, 0f, 0f, null)
    }
}

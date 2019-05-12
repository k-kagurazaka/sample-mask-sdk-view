package com.kkagurazaka.sample.sdk

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatTextView

class LyricView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    init {
        val textView = AppCompatTextView(context).apply {
            setText(R.string.text)
            textSize = 14f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        addView(textView, layoutParams)
    }
}

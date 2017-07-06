package com.ggdsn.titlelinecore

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.RelativeLayout
import android.widget.RelativeLayout.LayoutParams.MATCH_PARENT
import android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT

/**
 * Created by LiaoXingyu on 06/07/2017.
 */
class TitleLine @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val text1 = AppCompatTextView(context)
    private val text2 = AppCompatTextView(context)
    private var align: Align

    enum class Align {
        Left, Mid;

        companion object {
            fun fromInt(i: Int): Align {
                when (i) {
                    0 -> return Left
                    1 -> return Mid
                    else -> return Left
                }
            }
        }
    }

    init {
        text1.id = 1
        text2.id = 2
        val a = context.obtainStyledAttributes(attrs, R.styleable.TitleLine)
        align = Align.fromInt(a.getInt(R.styleable.TitleLine_align, 0))
        text1.text = a.getString(R.styleable.TitleLine_title)
        text2.text = a.getString(R.styleable.TitleLine_content)
        a.recycle()

        text1.setTextColor(ContextCompat.getColor(context, R.color.title_line_gray))
        text2.setTextColor(ContextCompat.getColor(context, if (text1.text.isNullOrEmpty()) R.color.title_line_gray else android.R.color.white))
        //由于getDimension的返回已经是PX了，如果用默认的方法，会再次放大。所以这里用COMPLEX_UNIT_PX
        text1.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.title_line_title_size))
        text2.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.title_line_content_size))

        val t1Lp = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val t2Lp = LayoutParams(MATCH_PARENT, WRAP_CONTENT)

        t2Lp.addRule(RelativeLayout.BELOW, text1.id)

        addView(text1, t1Lp)
        addView(text2, t2Lp)

        val gravity: Int
        when (align) {
            Align.Left -> {
                gravity = Gravity.START and Gravity.CENTER_VERTICAL
            }
            Align.Mid -> {
                gravity = Gravity.CENTER
            }
        }
        text1.gravity = gravity
        text2.gravity = gravity
    }

}
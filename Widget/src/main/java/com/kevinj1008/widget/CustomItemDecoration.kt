package com.kevinj1008.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kevinj1008.basecore.utils.isRtl

class CustomItemDecoration(
    context: Context
) : RecyclerView.ItemDecoration() {

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var dividerHeight: Float = 0f
    var startPadding: Float? = null
    var endPadding: Float? = null

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val adapter = parent.adapter
        if (position < 0 || adapter == null) return
        if (position > 0) {
            outRect.top = dividerHeight.toInt()
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        for (i in 0 until parent.childCount) {
            if (i == 0) {
                continue
            }
            val childView = parent.getChildAt(i)
            val top = childView.top - dividerHeight
            val bottom = childView.top
            val left: Float
            val right: Float
            if (parent.context.isRtl()) {
                left = startPadding ?: parent.paddingEnd.toFloat()
                right = endPadding ?: (parent.width - parent.paddingStart).toFloat()
            } else {
                left = startPadding ?: parent.paddingStart.toFloat()
                right = endPadding?: (parent.width - parent.paddingEnd).toFloat()
            }
            c.drawRect(left, top, right, bottom.toFloat(), paint)
        }
    }

    init {
        paint.color = ContextCompat.getColor(context, android.R.color.darker_gray)
        dividerHeight = 1f
    }
}
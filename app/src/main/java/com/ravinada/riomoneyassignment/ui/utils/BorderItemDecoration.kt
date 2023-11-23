package com.ravinada.riomoneyassignment.ui.utils

import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BorderItemDecoration(private val borderWidth: Int, borderColor: Int) :
    RecyclerView.ItemDecoration() {

    private val paint = Paint()

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth.toFloat()
        paint.color = borderColor
    }

    override fun getItemOffsets(
        outRect: android.graphics.Rect,
        view: android.view.View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        outRect.set(borderWidth, borderWidth, borderWidth, borderWidth)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.layoutManager as GridLayoutManager

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as GridLayoutManager.LayoutParams

            val left = child.left - params.leftMargin
            val top = child.top - params.topMargin
            val right = child.right + params.rightMargin
            val bottom = child.bottom + params.bottomMargin

            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }
}

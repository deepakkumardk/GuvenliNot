package com.kaancaliskan.guvenlinot.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kaancaliskan.guvenlinot.R

/**
 * Class to handle gestures
 */
abstract class SwipeToDeleteCallback(context: Context) : ItemTouchHelper.Callback() {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.baseline_delete_24)
    private val intrinsicWidth = deleteIcon!!.intrinsicWidth
    private val intrinsicHeight = deleteIcon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    /**
     * function that init movementFlags
     */
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    /**
     * function that enable/disable drag notes vertically
     */
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }
    /**
     * function that draw color and recycle bin under note frame
     */
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView

        if (!isCurrentlyActive) {
            clearCanvas(
                    c,
                    itemView.right + dX,
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),
                    itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val alpha = 1 - Math.abs(dX) / recyclerView.width
            viewHolder.itemView.alpha = alpha
        }

        // Draw the red delete background
        drawBackground(dX, itemView, c)

        // Draw delete icon
        drawDeleteIcon(itemView, dX, c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun drawDeleteIcon(itemView: View, dX: Float, c: Canvas) {
        val itemHeight = itemView.bottom - itemView.top
        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft: Int
        val deleteIconRight: Int
        if (dX >0) {
            deleteIconRight = itemView.left + deleteIconMargin + intrinsicWidth
            deleteIconLeft = itemView.left + deleteIconMargin
        } else {
            deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
            deleteIconRight = itemView.right - deleteIconMargin
        }
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        if (dX != 0F) {
            deleteIcon!!.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
            deleteIcon.draw(c)
        }
    }

    private fun drawBackground(dX: Float, itemView: View, c: Canvas) {
        background.color = when (willActionBeTriggered(dX, itemView.width)) {
            true -> Color.RED
            false -> Color.GRAY
        }
        if (dX >0) {
            background.setBounds(itemView.left + dX.toInt(), itemView.top, itemView.left, itemView.bottom)
        } else {
            background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        }
        background.draw(c)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }

    private fun willActionBeTriggered(dX: Float, viewWidth: Int): Boolean {
        return Math.abs(dX) >= viewWidth / 2
    }
}

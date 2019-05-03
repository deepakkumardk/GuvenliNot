package com.kaancaliskan.guvenlinot.util

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kaancaliskan.guvenlinot.R


abstract class SwipeToDeleteCallback(context: Context) : ItemTouchHelper.Callback() {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.baseline_delete_24)
    private val intrinsicWidth = deleteIcon!!.intrinsicWidth
    private val intrinsicHeight = deleteIcon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags =  ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }
    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }
    override fun onChildDraw(
            c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val alpha = 1 - Math.abs(dX) / recyclerView.width
            viewHolder.itemView.alpha = alpha
        }

        // Draw the red delete background
        background.color = when (willActionBeTriggered(dX, itemView.width)) {
            true -> Color.RED
            false -> Color.GRAY
        }
        if(dX >0) {
            background.setBounds(itemView.left + dX.toInt(), itemView.top, itemView.left, itemView.bottom)
        } else {
            background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        }
        background.draw(c)

        // Calculate position of delete icon
        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft : Int
        val deleteIconRight : Int
        if(dX >0) {
            deleteIconRight = itemView.left + deleteIconMargin + intrinsicWidth
            deleteIconLeft = itemView.left + deleteIconMargin
        } else {
            deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
            deleteIconRight = itemView.right - deleteIconMargin
        }
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        // Draw the delete icon
        deleteIcon!!.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
    private fun willActionBeTriggered(dX: Float, viewWidth: Int): Boolean {
        return Math.abs(dX) >= viewWidth / 2
    }
}
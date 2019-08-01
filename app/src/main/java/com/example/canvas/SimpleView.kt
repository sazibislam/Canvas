package com.example.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SimpleView(
  context: Context,
  attrs: AttributeSet
) : View(context, attrs) {

  private val paintColor = Color.BLACK
  // defines paint and canvas
  private var drawPaint: Paint? = null
  // Store circles to draw each time the user touches down
  private val path = Path()

  init {
    isFocusable = true
    isFocusableInTouchMode = true
    setupPaint()
  }

  // Draw each circle onto the view
  override fun onDraw(canvas: Canvas) {
    canvas.drawPath(path, drawPaint!!)
  }

  // Append new circle each time user presses on screen
  override fun onTouchEvent(event: MotionEvent): Boolean {
    val pointX = event.x
    val pointY = event.y
    // Checks for the event that occurs
    when (event.action) {
      MotionEvent.ACTION_DOWN -> {
        path.moveTo(pointX, pointY)
        return true
      }
      MotionEvent.ACTION_MOVE -> path.lineTo(pointX, pointY)
      else -> return false
    }
    // Force a view to draw again
    postInvalidate()
    return true
  }

  private fun setupPaint() {
    // Setup paint with color and stroke styles
    drawPaint = Paint()
    drawPaint!!.color = paintColor
    drawPaint!!.isAntiAlias = true
    drawPaint!!.strokeWidth = 5f
    drawPaint!!.style = Paint.Style.STROKE
    drawPaint!!.strokeJoin = Paint.Join.ROUND
    drawPaint!!.strokeCap = Paint.Cap.ROUND
  }

  //clear Canvas
  fun clearCanvas() {
    path.reset()
    invalidate()
  }
}

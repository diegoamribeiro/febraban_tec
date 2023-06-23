package com.cap.techsurvey.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import java.lang.Math.cos
import java.lang.Math.sin

class CircularDotsLoader(context: Context) : View(context) {
    private val dotRadius = 10f
    private val numDots = 10
    private val dotPaints = listOf(
        Paint().apply {
            color = Color.parseColor("#0A1F44")
            style = Paint.Style.FILL
        },
        Paint().apply {
            color = Color.parseColor("#061125")
            style = Paint.Style.FILL
        },
        Paint().apply {
            color = Color.parseColor("#56BDF6")
            style = Paint.Style.FILL
        }
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        val centerX = width / 2
        val centerY = height / 2

        val radius = if (centerX < centerY) centerX else centerY

        for (i in 0 until numDots) {
            val angle = Math.toRadians((i.toDouble() / numDots) * 360)

            val dotX = centerX + radius * kotlin.math.cos(angle) - dotRadius
            val dotY = centerY + radius * kotlin.math.sin(angle) - dotRadius

            val paint = dotPaints[i % dotPaints.size]
            canvas.drawCircle(dotX.toFloat(), dotY.toFloat(), dotRadius, paint)
        }
    }
}

package com.example.correlationapp.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.*

@Composable
fun ScatterPlot(
    xValues: List<Double>,
    yValues: List<Double>,
    title: String = "Scatter Plot"
) {
    if (xValues.isEmpty() || yValues.isEmpty() || xValues.size != yValues.size) {
        return
    }

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(340.dp)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        val width = size.width
        val height = size.height

        val marginLeft = 32f
        val marginBottom = 52f
        val marginTop = 82f
        val marginRight = 32f

        val plotWidth = width - marginLeft - marginRight
        val plotHeight = height - marginTop - marginBottom

        val minX = xValues.minOrNull() ?: 0.0
        val maxX = xValues.maxOrNull() ?: 1.0
        val minY = yValues.minOrNull() ?: 0.0
        val maxY = yValues.maxOrNull() ?: 1.0

        val xRange = maxX - minX + 0.0001
        val yRange = maxY - minY + 0.0001

        fun toCanvasX(x: Double): Float =
            marginLeft + ((x - minX) / xRange * plotWidth).toFloat()

        fun toCanvasY(y: Double): Float =
            marginTop + plotHeight - ((y - minY) / yRange * plotHeight).toFloat()

        drawGrid(
            marginLeft = marginLeft,
            marginTop = marginTop,
            plotWidth = plotWidth,
            plotHeight = plotHeight,
            steps = 5
        )

        xValues.indices.forEach { i ->
            val x = toCanvasX(xValues[i])
            val y = toCanvasY(yValues[i])

            drawCircle(
                color = Color(0xFF2196F3),
                radius = 5.5f,
                center = Offset(x, y)
            )
        }

        drawAxes(
            marginLeft = marginLeft,
            marginTop = marginTop,
            plotWidth = plotWidth,
            plotHeight = plotHeight
        )

        drawAxisLabels(
            textMeasurer = textMeasurer,
            minX = minX, maxX = maxX,
            minY = minY, maxY = maxY,
            marginLeft = marginLeft,
            marginTop = marginTop,
            plotWidth = plotWidth,
            plotHeight = plotHeight,
            steps = 5
        )

        drawText(
            textMeasurer = textMeasurer,
            text = title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            topLeft = Offset(width / 2 - 75f, 12f)
        )
    }
}

private fun DrawScope.drawGrid(
    marginLeft: Float,
    marginTop: Float,
    plotWidth: Float,
    plotHeight: Float,
    steps: Int
) {
    val gridColor = Color.LightGray.copy(alpha = 0.6f)

    for (i in 0..steps) {
        val x = marginLeft + (plotWidth / steps) * i
        drawLine(color = gridColor, start = Offset(x, marginTop), end = Offset(x, marginTop + plotHeight), strokeWidth = 1f)

        val y = marginTop + (plotHeight / steps) * i
        drawLine(color = gridColor, start = Offset(marginLeft, y), end = Offset(marginLeft + plotWidth, y), strokeWidth = 1f)
    }
}

private fun DrawScope.drawAxes(
    marginLeft: Float,
    marginTop: Float,
    plotWidth: Float,
    plotHeight: Float
) {
    val axisColor = Color.DarkGray

    drawLine(color = axisColor, start = Offset(marginLeft, marginTop + plotHeight),
        end = Offset(marginLeft + plotWidth, marginTop + plotHeight), strokeWidth = 2.5f)

    drawLine(color = axisColor, start = Offset(marginLeft, marginTop),
        end = Offset(marginLeft, marginTop + plotHeight), strokeWidth = 2.5f)

    drawLine(color = axisColor, start = Offset(marginLeft + plotWidth - 10f, marginTop + plotHeight - 8f),
        end = Offset(marginLeft + plotWidth, marginTop + plotHeight), strokeWidth = 2.5f)
    drawLine(color = axisColor, start = Offset(marginLeft + plotWidth - 10f, marginTop + plotHeight + 8f),
        end = Offset(marginLeft + plotWidth, marginTop + plotHeight), strokeWidth = 2.5f)

    drawLine(color = axisColor, start = Offset(marginLeft + 8f, marginTop + 10f),
        end = Offset(marginLeft, marginTop), strokeWidth = 2.5f)
    drawLine(color = axisColor, start = Offset(marginLeft - 8f, marginTop + 10f),
        end = Offset(marginLeft, marginTop), strokeWidth = 2.5f)
}

private fun DrawScope.drawAxisLabels(
    textMeasurer: TextMeasurer,
    minX: Double, maxX: Double,
    minY: Double, maxY: Double,
    marginLeft: Float,
    marginTop: Float,
    plotWidth: Float,
    plotHeight: Float,
    steps: Int
) {
    val labelStyle = TextStyle(fontSize = 12.sp, color = Color.DarkGray)

    for (i in 0..steps) {
        val value = minX + (maxX - minX) * i / steps
        val x = marginLeft + (plotWidth / steps) * i

        drawLine(color = Color.DarkGray, start = Offset(x, marginTop + plotHeight),
            end = Offset(x, marginTop + plotHeight + 8f), strokeWidth = 1.5f)

        val text = "%.1f".format(value)
        val measured = textMeasurer.measure(text, style = labelStyle)

        drawText(
            textMeasurer = textMeasurer,
            text = text,
            style = labelStyle,
            topLeft = Offset(x - measured.size.width / 2f, marginTop + plotHeight + 14f)
        )
    }

    for (i in 0..steps) {
        val value = minY + (maxY - minY) * i / steps
        val y = marginTop + plotHeight - (plotHeight / steps) * i

        drawLine(color = Color.DarkGray, start = Offset(marginLeft - 8f, y),
            end = Offset(marginLeft, y), strokeWidth = 1.5f)

        val text = "%.1f".format(value)
        val measured = textMeasurer.measure(text, style = labelStyle)

        drawText(
            textMeasurer = textMeasurer,
            text = text,
            style = labelStyle,
            topLeft = Offset(marginLeft - measured.size.width - 14f, y - measured.size.height / 2f)
        )
    }
}
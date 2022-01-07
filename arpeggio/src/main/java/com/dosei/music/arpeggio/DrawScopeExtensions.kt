package com.dosei.music.arpeggio

import android.graphics.Typeface
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

internal fun DrawScope.extractGeometry(inset: Float): Geometry {
    val gridSize = Size(
        width = size.width - positionSize.toPx(),
        height = size.height - positionSize.toPx()
    )
    return Geometry(
        fretSpaceHeight = gridSize.height / frets,
        stringSpaceWidth = gridSize.width / strings.dec(),
        canvasSize = size,
        inset = inset,
        positionSize = positionSize.toPx(),
        gridSize = gridSize,
        gridStart = Offset(inset, inset),
        gridEnd = Offset(inset + gridSize.width, inset + gridSize.height),
        bottomInset = size.height - (positionSize * 1.5f).toPx(),
        playIndicatorPadding = inset,
        playIndicatorSize = positionSize.toPx()
    )
}

internal fun DrawScope.drawGrid(
    columnWidth: Float,
    rowHeight: Float
) {
    drawRect(
        color = gridColor,
        style = Stroke(width = strokeWidth.toPx())
    )

    for (fret in 1 until frets) {
        drawHorizontalLine(fret * rowHeight)
    }

    for (string in 1 until strings.dec()) {
        drawVerticalLine(string * columnWidth)
    }
}

internal fun DrawScope.drawHorizontalLine(y: Float) =
    drawLine(
        color = gridColor,
        start = Offset(x = 0f, y = y),
        end = Offset(x = size.width, y = y),
        strokeWidth = strokeWidth.toPx()
    )

internal fun DrawScope.drawVerticalLine(x: Float) =
    drawLine(
        color = gridColor,
        start = Offset(x = x, y = 0f),
        end = Offset(x = x, y = size.height),
        strokeWidth = strokeWidth.toPx()
    )

fun DrawScope.drawPosition(
    fretCenter: Float,
    stringLine: Float
) {
    drawCircle(
        color = positionColor,
        radius = positionSize.toPx() / 2f,
        center = Offset(x = stringLine, y = fretCenter)
    )
}

fun DrawScope.drawFingerIndicator(
    finger: Finger,
    fretCenter: Float,
    stringCenter: Float
) {
    val paint = Paint().asFrameworkPaint().apply {
        textSize = (positionSize - 20.dp).toPx()
        color = fingerIndicatorColor.hashCode()
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
    }

    drawIntoCanvas {
        it.nativeCanvas.drawText(
            finger.number.toString(),
            stringCenter,
            (fretCenter + positionSize.toPx() * 0.2f),
            paint
        )
    }
}
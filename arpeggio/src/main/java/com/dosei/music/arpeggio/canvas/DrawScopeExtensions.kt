package com.dosei.music.arpeggio.canvas

import android.graphics.Typeface
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.dosei.music.arpeggio.Finger

internal fun DrawScope.extractGeometry(
    inset: Float,
    positionSize: Dp,
    strokeWidth: Dp,
    frets: Int,
    strings: Int
): Geometry {
    val gridSize = Size(
        width = size.width - positionSize.toPx(),
        height = size.height - (positionSize.toPx() * 1.5f + 8.dp.toPx())
    )
    return Geometry(
        strokeWidth = strokeWidth.toPx(),
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
    frets: Int,
    strings: Int,
    columnWidth: Float,
    rowHeight: Float,
    color: Color,
    strokeWidth: Dp
) {
    drawRect(
        color = color,
        style = Stroke(width = strokeWidth.toPx())
    )

    for (fret in 1 until frets) {
        drawHorizontalLine(fret * rowHeight, color, strokeWidth)
    }

    for (string in 1 until strings.dec()) {
        drawVerticalLine(string * columnWidth, color, strokeWidth)
    }
}

internal fun DrawScope.drawHorizontalLine(y: Float, color: Color, strokeWidth: Dp) =
    drawLine(
        color = color,
        start = Offset(x = 0f, y = y),
        end = Offset(x = size.width, y = y),
        strokeWidth = strokeWidth.toPx()
    )

internal fun DrawScope.drawVerticalLine(x: Float, color: Color, strokeWidth: Dp) =
    drawLine(
        color = color,
        start = Offset(x = x, y = 0f),
        end = Offset(x = x, y = size.height),
        strokeWidth = strokeWidth.toPx()
    )

internal fun DrawScope.drawPosition(
    fretCenter: Float,
    stringLine: Float,
    color: Color,
    positionSize: Dp
) {
    drawCircle(
        color = color,
        radius = positionSize.toPx() / 2f,
        center = Offset(x = stringLine, y = fretCenter)
    )
}

internal fun DrawScope.drawFingerIndicator(
    finger: Finger,
    fretCenter: Float,
    stringCenter: Float,
    color: Color,
    positionSize: Dp,
    textSize: TextUnit
) {
    val paint = Paint().asFrameworkPaint().apply {
        this.color = color.hashCode()
        this.textSize = textSize.toPx()
        this.textAlign = android.graphics.Paint.Align.CENTER
        this.typeface = Typeface.DEFAULT_BOLD
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

internal fun DrawScope.drawClosedStringIndicator(
    start: Offset,
    end: Offset,
    color: Color,
    strokeWidth: Dp
) {
    drawLine(
        color = color,
        strokeWidth = strokeWidth.toPx(),
        start = start,
        end = end
    )
    drawLine(
        color = color,
        strokeWidth = strokeWidth.toPx(),
        start = Offset(x = start.x, y = end.y),
        end = Offset(x = end.x, y = start.y)
    )
}

internal fun DrawScope.drawOpenStringIndicator(
    center: Offset,
    color: Color,
    strokeWidth: Dp,
    positionSize: Dp
) {
    drawCircle(
        color = color,
        style = Stroke(width = strokeWidth.toPx()),
        radius = (positionSize.toPx() / 2f) - strokeWidth.toPx(),
        center = center
    )
}

internal fun DrawScope.drawBarre(
    initialStringCenter: Float,
    finalStringCenter: Float,
    fretCenter: Float,
    color: Color,
    positionSize: Dp
) {
    val halfPositionSize = positionSize.toPx() / 2f
    val initialX = initialStringCenter - halfPositionSize
    drawRoundRect(
        color = color,
        topLeft = Offset(
            x = initialX,
            y = fretCenter - halfPositionSize
        ),
        size = Size(
            width = finalStringCenter - initialX + halfPositionSize,
            height = positionSize.toPx()
        ),
        cornerRadius = CornerRadius(x = halfPositionSize, y = halfPositionSize)
    )
}
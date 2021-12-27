package com.dosei.music.arpeggio

import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GenericGrid(
    strings: Int,
    frets: Int,
    strokeWidth: Dp,
    modifier: Modifier = Modifier,
    color: Color,
    content: GridScope.() -> Unit
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val fretSize = size.height / frets
        val stringSize = size.width / strings.dec()
        val strokeWidthInPxs = strokeWidth.toPx()

        drawRect(
            style = Stroke(width = strokeWidthInPxs),
            color = color,
            topLeft = Offset.Zero,
            size = size
        )

        var fretY = fretSize
        for (index in 1 until frets) {
            drawLine(
                color = color,
                start = Offset(x = 0f, y = fretY),
                end = Offset(x = size.width, y = fretY),
                strokeWidth = strokeWidthInPxs
            )
            fretY += fretSize
        }

        var stringX = stringSize
        for (index in 1 until strings) {
            drawLine(
                color = color,
                start = Offset(x = stringX, y = 0f),
                end = Offset(x = stringX, y = size.height),
                strokeWidth = strokeWidthInPxs
            )
            stringX += stringSize
        }

        val scope = GridScope(
            stringSize = stringSize,
            fretSize = fretSize,
            drawScope = this,
            positionColor = Color.Black,
            textColor = Color.White
        )
        content(scope)
    }
}

class GridScope(
    private val stringSize: Float,
    private val fretSize: Float,
    private val drawScope: DrawScope,
    private val positionColor: Color,
    private val textColor: Color
) {
    private val paint = Paint().asFrameworkPaint().apply {
        textSize = drawScope.run { 24.dp.toPx() }
        color = textColor.hashCode()
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
    }

    fun drawPositionAt(
        finger: Finger?,
        positionSize: Dp = 20.dp,
        string: Int,
        fret: Int
    ) {
        val positionY = fret * fretSize - (fretSize / 2f)
        val positionX = string * stringSize

        drawScope.drawCircle(
            color = positionColor,
            radius = drawScope.run { positionSize.toPx() },
            center = Offset(x = positionX, y = positionY)
        )
        drawScope.drawIntoCanvas {
            it.nativeCanvas.drawText(
                finger?.number?.toString().orEmpty(),
                positionX,
                positionY + drawScope.run { 8.dp.toPx() },
                paint
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGenericGrid() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        GenericGrid(
            modifier = Modifier.padding(90.dp),
            strings = 6,
            frets = 4,
            strokeWidth = 3.dp,
            color = Color.Black
        ) {
            drawPositionAt(
                finger = Finger.Ring,
                string = 1,
                fret = 3
            )

            drawPositionAt(
                finger = Finger.Middle,
                string = 2,
                fret = 2
            )

            drawPositionAt(
                finger = Finger.Index,
                string = 4,
                fret = 1
            )
        }
    }
}
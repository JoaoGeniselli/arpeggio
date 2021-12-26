package com.dosei.music.arpeggio

import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val WIDTH_STROKE = 3.dp

@Composable
fun Chord(
    modifier: Modifier = Modifier,
    name: String,
    positions: List<Position>
) {
    Canvas(modifier = modifier.fillMaxWidth()) {
        val rectSize = Size(width = size.width, height = size.height)
        drawRect(
            color = Color.Black,
            topLeft = Offset.Zero,
            size = rectSize,
            style = Stroke(
                width = WIDTH_STROKE.toPx(),
                pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(10f, 10f))
            )
        )
    }
    Grid(
        chordName = "G",
        modifier = modifier.padding(32.dp),
        positions = positions
    )
}

@Composable
private fun Grid(
    chordName: String,
    modifier: Modifier = Modifier,
    lineColor: Color = Color.Black,
    frets: Int = 4,
    strings: Int = 6,
    strokeWidth: Dp = 3.dp,
    startingFret: Int = 0,
    positions: List<Position>
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            color = lineColor,
            text = chordName,
            fontSize = 40.sp,
            modifier = Modifier.align(CenterHorizontally)
        )
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidthInPxs = strokeWidth.toPx()

            val gridStartY = 30.dp.toPx()
            val gridStartX = 0f

            val gridEndY = size.height - 60.dp.toPx()

            //region Start rect
            val rectSize = Size(width = size.width + strokeWidthInPxs, height = gridStartY)
            drawRect(
                color = lineColor,
                topLeft = Offset(x = gridStartX - (strokeWidthInPxs / 2f), y = 0f),
                size = rectSize
            )
            //endregion

            //region Draw frets
            val fretHeight = (gridEndY - gridStartY) / frets.inc()
            var fretY = gridStartY
            for (index in 0..frets + 1) {
                drawLine(
                    color = lineColor,
                    start = Offset(x = gridStartX, y = fretY),
                    end = Offset(x = size.width, y = fretY),
                    strokeWidth = strokeWidthInPxs
                )
                fretY += fretHeight
            }
            //endregion

            //region Draw lines
            val stringLinesToDraw = strings - 2

            val stringWidth = (size.width - gridStartX) / stringLinesToDraw.inc()
            var lineX = gridStartX
            for (index in 0..stringLinesToDraw + 1) {
                drawLine(
                    color = lineColor,
                    start = Offset(x = lineX, y = gridStartY),
                    end = Offset(x = lineX, y = gridEndY),
                    strokeWidth = strokeWidthInPxs
                )
                lineX += stringWidth
            }
            //endregion

            //region Draw positions
            positions.forEach { position ->
                if (position.string < strings && position.fret > 0) {
                    val fretMultiplier = position.fret
                    val positionY = fretMultiplier * fretHeight - (fretHeight / 2f) + gridStartY
                    val positionX = position.string * stringWidth + gridStartX

                    drawCircle(
                        color = lineColor,
                        radius = 20.dp.toPx(),
                        center = Offset(x = positionX, y = positionY)
                    )

                    val paint = Paint().asFrameworkPaint().apply {
                        textSize = 24.dp.toPx()
                        color = Color.White.hashCode()
                        textAlign = android.graphics.Paint.Align.CENTER
                        typeface = Typeface.DEFAULT_BOLD
                    }

                    drawIntoCanvas {
                        it.nativeCanvas.drawText(
                            position.finger?.number.toString(),
                            positionX,
                            positionY + 8.dp.toPx(),
                            paint
                        )
                    }
                }
            }
            //endregion
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChord() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Chord(
            modifier = Modifier.padding(16.dp),
            name = "G",
            positions = listOf(
                Position(0, 3, Finger.MIDDLE),
                Position(1, 2, Finger.INDEX),
                Position.openString(2),
                Position.openString(3),
                Position(4, 3, Finger.RING),
                Position(5, 3, Finger.PINKY)
            )
        )
    }
}
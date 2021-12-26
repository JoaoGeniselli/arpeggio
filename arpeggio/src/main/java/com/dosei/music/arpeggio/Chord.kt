package com.dosei.music.arpeggio

import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dosei.music.arpeggio.Finger.*

private val WIDTH_STROKE = 3.dp

@Composable
fun Chord(
    modifier: Modifier = Modifier,
    name: String,
    positions: List<Position>,
    strings: Int = 6,
    frets: Int = 4,
    startingFret: Int = 0
) {
    val positionSize = 32.dp
    val gridPadding = 28.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            color = Color.Black,
            text = name,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(CenterHorizontally)
        )
        Rect(
            modifier = Modifier
                .height(16.dp)
                .padding(horizontal = gridPadding - 2.dp)
        )
        Grid(
            modifier = Modifier
                .padding(horizontal = gridPadding)
                .weight(1f),
            positions = positions,
            strings = strings,
            frets = frets
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)) {
            for (string in 0 until strings) {
                val canBePlayed = positions.any { it.string == string }
                StringIndicator(
                    modifier = Modifier
                        .weight(1f)
                        .height(height = positionSize),
                    canBePlayed = canBePlayed
                )
            }
        }
    }
}

@Composable
fun StringIndicator(
    modifier: Modifier = Modifier,
    canBePlayed: Boolean
) {
    val resource = if (canBePlayed) R.drawable.ic_circle_48 else R.drawable.ic_cross_48
    Image(modifier = modifier, painter = painterResource(id = resource), contentDescription = "")
}

@Composable
private fun Grid(
    modifier: Modifier = Modifier,
    lineColor: Color = Color.Black,
    frets: Int = 4,
    strings: Int = 6,
    strokeWidth: Dp = 3.dp,
    startingFret: Int = 0,
    positions: List<Position>
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val strokeWidthInPxs = strokeWidth.toPx()

        //region Draw frets
        val fretHeight = size.height / frets.inc()
        var fretY = 0f
        for (index in 0..frets + 1) {
            drawLine(
                color = lineColor,
                start = Offset(x = 0f, y = fretY),
                end = Offset(x = size.width, y = fretY),
                strokeWidth = strokeWidthInPxs
            )
            fretY += fretHeight
        }
        //endregion

        //region Draw lines
        val stringLinesToDraw = strings - 2

        val stringWidth = size.width / stringLinesToDraw.inc()
        var lineX = 0f
        for (index in 0..stringLinesToDraw + 1) {
            drawLine(
                color = lineColor,
                start = Offset(x = lineX, y = 0f),
                end = Offset(x = lineX, y = size.height),
                strokeWidth = strokeWidthInPxs
            )
            lineX += stringWidth
        }
        //endregion

        //region Draw positions
            positions.filter { it.string < strings && it.fret > startingFret }.forEach { position ->
                val fretMultiplier = position.fret
                val positionY = fretMultiplier * fretHeight - (fretHeight / 2f)
                val positionX = position.string * stringWidth

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
        //endregion

        // region draw open strings
//            positions.filter { it.string < strings && it.fret == startingFret }
//                .forEach { position ->
//
//                }
        //endregion
    }
}

@Composable
private fun NewGrid(
    modifier: Modifier = Modifier,
    lineColor: Color = Color.Black,
    frets: Int = 4,
    strings: Int = 6,
    strokeWidth: Dp = WIDTH_STROKE
) {
    VerticalGrid(
        modifier = modifier,
        lineColor = lineColor,
        frets = frets,
        strings = strings,
        strokeWidth = strokeWidth
    )
}

@Composable
private fun VerticalGrid(
    modifier: Modifier = Modifier,
    lineColor: Color = Color.Black,
    frets: Int = 4,
    strings: Int = 6,
    strokeWidth: Dp = WIDTH_STROKE
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val strokeWidthInPxs = strokeWidth.toPx()

        val fretHeight = size.height / frets.inc()
        var fretY = 0f
        for (index in 0..frets + 1) {
            drawLine(
                color = lineColor,
                start = Offset(x = 0f, y = fretY),
                end = Offset(x = size.width, y = fretY),
                strokeWidth = strokeWidthInPxs
            )
            fretY += fretHeight
        }

        val stringLinesToDraw = strings - 2

        val stringWidth = size.width / stringLinesToDraw.inc()
        var lineX = 0f
        for (index in 0..stringLinesToDraw + 1) {
            drawLine(
                color = lineColor,
                start = Offset(x = lineX, y = 0f),
                end = Offset(x = lineX, y = size.height),
                strokeWidth = strokeWidthInPxs
            )
            lineX += stringWidth
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChord() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Chord(
            name = "E#",
            positions = listOf(
                Position(0, 3, Middle),
                Position(1, 2, Index),
                Position.openString(2),
                Position.openString(3),
                Position(4, 3, Ring),
                Position(5, 3, Pinky),
            )
        )
    }
}
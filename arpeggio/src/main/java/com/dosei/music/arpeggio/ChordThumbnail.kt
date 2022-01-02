package com.dosei.music.arpeggio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ThumbnailChordDiagram(
    modifier: Modifier = Modifier,
    name: String,
    positions: List<Position>,
    strings: Int = 6,
    frets: Int = 4,
    startingFret: Int = 0,
    colors: Colors = Colors(),
    strokeWidth: Dp = 1.dp
) {
    val gridPadding = 10.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            color = colors.text,
            fontSize = 18.sp,
            text = name,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 4.dp)
        )
        Rect(
            color = colors.grid,
            modifier = Modifier
                .height(2.dp)
                .padding(horizontal = gridPadding - (strokeWidth / 2))
        )
        ThumbnailGrid(
            modifier = Modifier
                .padding(horizontal = gridPadding)
                .weight(1f),
            strings = strings,
            frets = frets,
            strokeWidth = strokeWidth,
            colors = colors,
            startingFret = startingFret
        ) {
            positions.forEach { position ->
                drawPositionAt(
                    string = position.string,
                    fret = position.fret
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            for (string in 0 until strings) {
                val canBePlayed = positions.any { it.string == string }
                StringIndicator(
                    modifier = Modifier
                        .weight(1f)
                        .height(height = 8.dp),
                    canBePlayed = canBePlayed,
                    color = colors.position
                )
            }
        }
    }
}

@Composable
fun ThumbnailGrid(
    strings: Int,
    frets: Int,
    strokeWidth: Dp,
    modifier: Modifier = Modifier,
    colors: Colors,
    startingFret: Int,
    content: ThumbnailGridScope.() -> Unit
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val fretSize = size.height / frets
        val stringSize = size.width / strings.dec()
        val strokeWidthInPxs = strokeWidth.toPx()

        drawRect(
            style = Stroke(width = strokeWidthInPxs),
            color = colors.grid,
            topLeft = Offset.Zero,
            size = size
        )

        var fretY = fretSize
        for (index in 1 until frets) {
            drawLine(
                color = colors.grid,
                start = Offset(x = 0f, y = fretY),
                end = Offset(x = size.width, y = fretY),
                strokeWidth = strokeWidthInPxs
            )
            fretY += fretSize
        }

        var stringX = stringSize
        for (index in 1 until strings) {
            drawLine(
                color = colors.grid,
                start = Offset(x = stringX, y = 0f),
                end = Offset(x = stringX, y = size.height),
                strokeWidth = strokeWidthInPxs
            )
            stringX += stringSize
        }

        val scope = ThumbnailGridScope(
            stringSize = stringSize,
            fretSize = fretSize,
            drawScope = this,
            startingFret = startingFret,
            positionColor = colors.position
        )
        content(scope)
    }
}

class ThumbnailGridScope(
    private val stringSize: Float,
    private val fretSize: Float,
    private val drawScope: DrawScope,
    private val startingFret: Int,
    private val positionColor: Color,
) {
    fun drawPositionAt(
        positionSize: Dp = 8.dp,
        string: Int,
        fret: Int
    ) {
        if (fret <= startingFret) return

        val positionY = fret * fretSize - (fretSize / 2f)
        val positionX = string * stringSize

        drawScope.drawCircle(
            color = positionColor,
            radius = drawScope.run { positionSize.toPx() },
            center = Offset(x = positionX, y = positionY)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewChordThumbnail() {
    Surface(modifier = Modifier.size(width = 150.dp, height = 200.dp), color = Color.White) {
        ThumbnailChordDiagram(
            name = "C",
            colors = Colors(
                text = Color.Black,
                grid = Color.Black,
                position = Color.Black,
                positionText = Color.White
            ),
            positions = listOf(
                Position(1, 3, Finger.Ring),
                Position(2, 2, Finger.Middle),
                Position.openString(3),
                Position(4, 1, Finger.Index),
                Position.openString(5)
            )
        )
    }
}
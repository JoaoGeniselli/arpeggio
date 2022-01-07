package com.dosei.music.arpeggio

import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dosei.music.arpeggio.Finger.*

private val WIDTH_STROKE = 3.dp

@Composable
fun ChordDiagram(
    modifier: Modifier = Modifier,
    name: String,
    positions: List<LegacyPosition>,
    strings: Int = 6,
    frets: Int = 4,
    startingFret: Int = 0,
    colors: Colors = Colors(),
    strokeWidth: Dp = WIDTH_STROKE
) {
    val gridPadding = 30.dp

    Row {
        Text(
            color = colors.text,
            text = "5ª",
            fontSize = 40.sp,
            modifier = Modifier.padding(top = 50.dp)
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                color = colors.text,
                text = name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(CenterHorizontally)
            )
            Rect(
                color = colors.grid,
                modifier = Modifier
                    .height(16.dp)
                    .padding(horizontal = gridPadding - (strokeWidth / 2))
            )
            GenericGrid(
                modifier = Modifier
                    .padding(horizontal = gridPadding)
                    .weight(1f),
                strings = strings,
                frets = frets,
                strokeWidth = WIDTH_STROKE,
                colors = colors
            ) {
                positions.forEach { position ->
                    drawPositionAt(
                        finger = position.finger,
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
                            .height(height = 32.dp),
                        canBePlayed = canBePlayed,
                        color = colors.position
                    )
                }
            }
        }
    }

}

@Composable
private fun Grid(
    modifier: Modifier = Modifier,
    colors: Colors,
    frets: Int = 4,
    strings: Int = 6,
    strokeWidth: Dp = WIDTH_STROKE,
    startingFret: Int = 0,
    positionSize: Dp = 40.dp,
    positions: List<LegacyPosition>
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val strokeWidthInPxs = strokeWidth.toPx()

        val fretHeight = size.height / frets
        drawFrets(
            frets = frets,
            height = fretHeight,
            color = colors.grid,
            strokeWidth = strokeWidthInPxs
        )

        val stringWidth = size.width / strings.dec()
        drawStrings(
            stringAmount = strings,
            width = stringWidth,
            color = colors.grid,
            strokeWidth = strokeWidthInPxs
        )

        drawPositions(
            positionColor = colors.position,
            textColor = colors.positionText,
            positions = positions,
            strings = strings,
            startingFret = startingFret,
            fretHeight = fretHeight,
            stringWidth = stringWidth
        )
    }
}

private fun DrawScope.drawFrets(
    frets: Int,
    height: Float,
    color: Color,
    strokeWidth: Float
) {
    var fretY = 0f
    val fretStartX = strokeWidth / 2f * -1
    val fretEndX = size.width + strokeWidth / 2f
    for (index in 0..frets) {
        drawLine(
            color = color,
            start = Offset(x = fretStartX, y = fretY),
            end = Offset(x = fretEndX, y = fretY),
            strokeWidth = strokeWidth
        )
        fretY += height
    }
}

private fun DrawScope.drawStrings(
    stringAmount: Int,
    color: Color,
    width: Float,
    strokeWidth: Float
) {
    var lineX = 0f
    for (index in 0 until stringAmount) {
        drawLine(
            color = color,
            start = Offset(x = lineX, y = 0f),
            end = Offset(x = lineX, y = size.height),
            strokeWidth = strokeWidth
        )
        lineX += width
    }
}

private fun DrawScope.drawPositions(
    positionColor: Color,
    textColor: Color,
    positions: List<LegacyPosition>,
    strings: Int,
    startingFret: Int,
    fretHeight: Float,
    stringWidth: Float
) {
    val paint = Paint().asFrameworkPaint().apply {
        textSize = 24.dp.toPx()
        color = textColor.hashCode()
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
    }

    positions.filter { it.string < strings && it.fret > startingFret }.forEach { position ->
        val fretMultiplier = position.fret
        val positionY = fretMultiplier * fretHeight - (fretHeight / 2f)
        val positionX = position.string * stringWidth

        drawCircle(
            color = positionColor,
            radius = 20.dp.toPx(),
            center = Offset(x = positionX, y = positionY)
        )
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

@Preview(showBackground = true)
@Composable
fun PreviewChord() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        ChordDiagram(
            name = "C",
            colors = Colors(
                text = Color.Green,
                grid = Color.Black,
                position = Color.Red,
                positionText = Color.White
            ),
            positions = listOf(
                LegacyPosition(1, 3, Ring),
                LegacyPosition(2, 2, Middle),
                LegacyPosition.openString(3),
                LegacyPosition(4, 1, Index),
                LegacyPosition.openString(5)
            )
        )
    }
}
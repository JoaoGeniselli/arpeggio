package com.dosei.music.arpeggio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun GridTop(
    modifier: Modifier = Modifier,
    scope: GridTopScope.() -> Unit
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val inset = positionSize.toPx() / 2f
        val geometry = extractGeometry(inset)
        inset(horizontal = inset - strokeWidth.toPx()) {
            drawRect(gridColor, size = Size(width = size.width, height = inset))
        }
        inset(
            left = inset,
            right = inset,
            top = inset,
            bottom = inset * 2 + 8.dp.toPx()
        ) {
            drawGrid(
                geometry.stringSpaceWidth,
                geometry.fretSpaceHeight
            )
        }
        GridTopScope(geometry, this)
            .apply(scope)
            .commit()
    }
}

fun DrawScope.drawBarre(
    initialStringCenter: Float,
    finalStringCenter: Float,
    fretCenter: Float
) {
    val initialX = initialStringCenter - positionSize.toPx() / 2f
    drawRoundRect(
        color = positionColor,
        topLeft = Offset(
            x = initialX,
            y = fretCenter - positionSize.toPx() / 2f
        ),
        size = Size(
            width = finalStringCenter - initialX + positionSize.toPx() / 2f,
            height = positionSize.toPx()
        ),
        cornerRadius = CornerRadius(x = positionSize.toPx() / 2f, y = positionSize.toPx() / 2f)
    )
}

class GridTopScope(
    private val geometry: Geometry,
    private val drawScope: DrawScope
) {
    private val stringUsage = (0 until strings).map { it to false }.toMap().toMutableMap()
    private var initialFret = 0


    fun commit() {
        if (initialFret != 0) {
            // TODO("DRAW INITIAL FRET INDICATOR")
        }
        stringUsage.forEach { entry ->
            val string = entry.key
            val isUsed = entry.value
            if (isUsed) {
                drawScope.run {
                    drawCircle(
                        color = positionColor,
                        style = Stroke(width = strokeWidth.toPx()),
                        radius = (positionSize.toPx() / 2f) - strokeWidth.toPx(),
                        center = geometry.centerOfStringIndicator(string)
                    )
                }
            } else {
                drawScope.run {
                    drawLine(
                        color = positionColor,
                        strokeWidth = strokeWidth.toPx(),
                        start = geometry.topLeftOfStringIndicator(string),
                        end = geometry.bottomRightOfStringIndicator(string)
                    )
                    drawLine(
                        color = positionColor,
                        strokeWidth = strokeWidth.toPx(),
                        start = geometry.bottomLeftOfStringIndicator(string),
                        end = geometry.topRightOfStringIndicator(string)
                    )
                }
            }
        }
    }

    fun draw(barre: Barre) {
        drawScope.drawBarre(
            initialStringCenter = geometry.centerOfString(barre.strings.first),
            finalStringCenter = geometry.centerOfString(barre.strings.last),
            fretCenter = geometry.centerOfFret(barre.fret)
        )

        barre.finger?.let {
            drawScope.drawFingerIndicator(
                finger = it,
                fretCenter = geometry.centerOfFret(barre.fret),
                stringCenter = geometry.centerOfString(barre.strings.first)
            )
        }

        initialFret = max(initialFret, barre.fret)
        stringUsage.putAll(barre.strings.map { it to true })
    }

    fun draw(position: Position) {
        val fretCenter = geometry.centerOfFret(position.fret)
        val stringCenter = geometry.centerOfString(position.string)

        drawScope.drawPosition(
            fretCenter = fretCenter,
            stringLine = stringCenter
        )

        position.finger?.let {
            drawScope.drawFingerIndicator(
                finger = it,
                fretCenter = fretCenter,
                stringCenter = stringCenter
            )
        }
        initialFret = max(initialFret, position.fret)
        stringUsage[position.string] = true
    }

    fun draw(openString: OpenString) {
        stringUsage[openString.string] = true
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewChordThumbnail() {
    Surface(modifier = Modifier.size(600.dp), color = Color.White) {
        GridTop(
            modifier = Modifier.padding(50.dp)
        ) {
            draw(
                Barre(
                    fret = 2,
                    strings = 1..5,
                    finger = Finger.Index
                )
            )
            draw(Position(fret = 3, string = 4, finger = Finger.Middle))
            draw(Position(fret = 4, string = 3, finger = Finger.Pinky))
            draw(Position(fret = 4, string = 2, finger = Finger.Ring))
        }
    }
}
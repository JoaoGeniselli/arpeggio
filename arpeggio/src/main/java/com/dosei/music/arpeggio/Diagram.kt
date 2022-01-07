package com.dosei.music.arpeggio

import android.graphics.Typeface
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Diagram(name: String, components: List<Component>) {

     GridTop {

     }

}

sealed class Component

class Barre(
    val fret: Int,
    val strings: IntRange,
    val finger: Finger? = null
) : Component()

class Position(
    val fret: Int,
    val string: Int,
    val finger: Finger? = null
) : Component()

class OpenString(val string: Int) : Component()

class DiagramScope(
    private val drawScope: DrawScope,
    private val geometry: Geometry
) {
    private val paint = Paint().asFrameworkPaint().apply {
        textSize = drawScope.run { 24.dp.toPx() }
        color = Color.White.hashCode()
        textAlign = android.graphics.Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
    }

    init {
        // TODO: Draw non played string indicators
    }

    @Composable
    fun Barre(
        fret: Int,
        strings: IntRange,
        finger: Finger? = null
    ) {
        // TODO: Draw Barre
    }

    @Composable
    fun OpenString(number: Int) {
        // TODO: Draw string indicator
    }

    @Composable
    fun Position(
        fret: Int,
        string: Int,
        finger: Finger? = null
    ) {
        drawScope.drawCircle(
            color = Color.Black,
            radius = positionSize.toPixels() / 2f,
            center = Offset(
                x = geometry.centerOfString(string),
                y = geometry.centerOfFret(fret)
            )
        )
        // TODO: Draw finger indicator
    }
}

val gridColor = Color.Black
val positionSize = 40.dp
val strokeWidth = 2.dp
val positionColor = Color.Black
val fingerIndicatorColor = Color.White
const val frets = 5
const val strings = 6

@Preview(showBackground = true)
@Composable
private fun PreviewDiagram() {
    Surface(color = Color.White, modifier = Modifier.size(500.dp)) {
        Diagram(name = "D", components = listOf())
    }
}
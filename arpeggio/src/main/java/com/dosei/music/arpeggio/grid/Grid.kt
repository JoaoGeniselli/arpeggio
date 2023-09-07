package com.dosei.music.arpeggio.grid

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dosei.music.arpeggio.Barre
import com.dosei.music.arpeggio.DefaultInitialFret
import com.dosei.music.arpeggio.Finger
import com.dosei.music.arpeggio.Position
import com.dosei.music.arpeggio.canvas.drawGrid
import com.dosei.music.arpeggio.canvas.extractGeometry
import com.dosei.music.arpeggio.theme.DiagramTheme

@Composable
internal fun Grid(
    modifier: Modifier = Modifier,
    initialFret: Int = DefaultInitialFret,
    scope: GridScope.() -> Unit
) {
    val sizes = DiagramTheme.sizes
    val colors = DiagramTheme.colors
    val typography = DiagramTheme.typography
    val frets = DiagramTheme.frets
    val strings = DiagramTheme.strings

    Canvas(modifier = modifier.fillMaxSize()) {
        val inset = sizes.position.toPx() / 2f
        val geometry = extractGeometry(
            inset = inset,
            positionSize = sizes.position,
            strokeWidth = sizes.strokeWidth,
            frets = frets,
            strings = strings
        )
        inset(horizontal = inset - sizes.strokeWidth.toPx()) {
            drawRect(colors.grid, size = Size(width = size.width, height = inset))
        }
        inset(
            left = inset,
            right = inset,
            top = inset,
            bottom = inset * 2 + 8.dp.toPx()
        ) {
            drawGrid(
                frets = frets,
                strings = strings,
                columnWidth = geometry.stringSpaceWidth,
                rowHeight = geometry.fretSpaceHeight,
                color = colors.grid,
                strokeWidth = sizes.strokeWidth
            )
        }
        GridScope(initialFret, colors, geometry, sizes, typography, strings, this)
            .apply(scope)
            .commit()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewChordThumbnail() {
    Surface(modifier = Modifier.size(600.dp), color = Color.White) {
        Grid(
            modifier = Modifier.padding(50.dp),
            scope = {
                draw(
                    Barre(
                        fret = 2,
                        strings = 1..5,
                        finger = Finger.Index
                    )
                )
                draw(Position(fret = 3, string = 2, finger = Finger.Middle))
                draw(Position(fret = 4, string = 3, finger = Finger.Pinky))
                draw(Position(fret = 4, string = 4, finger = Finger.Ring))
            },
            initialFret = 1
        )
    }
}
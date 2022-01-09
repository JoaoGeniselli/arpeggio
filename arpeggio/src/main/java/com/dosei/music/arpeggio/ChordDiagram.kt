package com.dosei.music.arpeggio

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dosei.music.arpeggio.grid.Grid
import com.dosei.music.arpeggio.theme.DiagramTheme

@Composable
fun ChordDiagram(
    modifier: Modifier = Modifier,
    name: String,
    components: List<Component>
) {
    ChordDiagram(
        modifier = modifier,
        name = AnnotatedString(name),
        components = components
    )
}

@Composable
fun ChordDiagram(
    modifier: Modifier = Modifier,
    name: AnnotatedString,
    components: List<Component>
) {
    val typography = DiagramTheme.typography
    val formatInitialFret = DiagramTheme.formatInitialFret
    val initialFretRange = DiagramTheme.initialFretRange

    val firstFret = if (components.fitsInFretRange(initialFretRange)) {
        DefaultInitialFret
    } else {
        components.firstFret()
    }
    Column(
        modifier = modifier
            .defaultMinSize(minWidth = MinWidth, minHeight = MinHeight)
    ) {
        Text(
            text = name,
            style = typography.name,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(modifier = Modifier.padding(top = 8.dp)) {
            if (firstFret != DefaultInitialFret) {
                Text(
                    modifier = Modifier.width(40.dp),
                    textAlign = TextAlign.Center,
                    text = formatInitialFret(firstFret),
                    style = typography.firstFretIndicator
                )
            } else {
                Spacer(modifier = Modifier.width(40.dp))
            }
            Grid(
                modifier = Modifier.weight(1f),
                initialFret = firstFret
            ) {
                components.forEach { draw(it) }
            }
            Spacer(modifier = Modifier.width(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDiagram() {
    Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
        ChordDiagram(
            modifier = Modifier.padding(16.dp),
            name = "Bm",
            components = listOf(
                Barre(fret = 2, strings = 1..5, finger = Finger.Index),
                Position(fret = 3, string = 4, finger = Finger.Middle),
                Position(fret = 4, string = 3, finger = Finger.Pinky),
                Position(fret = 5, string = 2, finger = Finger.Ring),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewInitialFret() {
    Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
        ChordDiagram(
            modifier = Modifier.padding(16.dp),
            name = "B",
            components = listOf(
                Barre(fret = 7, strings = 1..6, finger = Finger.Index),
                Position(fret = 8, string = 3, finger = Finger.Middle),
                Position(fret = 9, string = 4, finger = Finger.Pinky),
                Position(fret = 9, string = 5, finger = Finger.Ring),
            )
        )
    }
}
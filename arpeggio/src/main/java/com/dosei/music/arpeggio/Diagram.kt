package com.dosei.music.arpeggio

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val gridColor = Color.Black
val nameTextColor = gridColor
val positionColor = Color.Black
val fingerIndicatorColor = Color.White

val nameFontSize = 40.sp
val positionSize = 40.dp
val strokeWidth = 2.dp

val formatInitialFret = if (Locale.current == Locale("pt-br")) {
    FormatInitialFret.ptBr()
} else {
    FormatInitialFret.enUs()
}

const val frets = 5
const val strings = 6
const val defaultInitialFret = 1
val defaultFretRange: IntRange get() = defaultInitialFret..frets

@Composable
fun Diagram(
    modifier: Modifier = Modifier,
    name: String,
    components: List<Component>
) {
    val firstFret = if (components.fitsInFretRange(defaultFretRange)) {
        defaultInitialFret
    } else {
        components.firstFret()
    }
    Column(modifier = modifier) {
        Text(
            text = name,
            fontSize = nameFontSize,
            color = nameTextColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(modifier = Modifier.padding(top = 8.dp)) {
            if (firstFret != defaultInitialFret) {
                Text(
                    modifier = Modifier.width(40.dp),
                    textAlign = TextAlign.Center,
                    text = formatInitialFret(firstFret),
                    fontSize = 24.sp,
                    color = nameTextColor,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Spacer(modifier = Modifier.width(40.dp))
            }
            GridTop(
                modifier = Modifier.weight(1f),
                initialFret = firstFret
            ) {
                components.forEach { draw(it) }
            }
            Spacer(modifier = Modifier.width(40.dp))
        }
    }
}

@Composable
fun TextUnit.toDp(): Dp = with(LocalDensity.current) { toDp() }

private fun List<Component>.fitsInFretRange(fretRange: IntRange): Boolean =
    fretRange.run {
        contains(firstFret()) and contains(lastFret())
    }

fun List<Component>.firstFret(): Int =
    minOf {
        when (it) {
            is Barre -> it.fret
            is Position -> it.fret
            else -> Int.MAX_VALUE
        }
    }

fun List<Component>.lastFret(): Int =
    maxOf {
        when (it) {
            is Barre -> it.fret
            is Position -> it.fret
            else -> 0
        }
    }

@Preview(showBackground = true)
@Composable
private fun PreviewDiagram() {
    Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
        Diagram(
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
        Diagram(
            modifier = Modifier.padding(16.dp),
            name = "Bm",
            components = listOf(
                Barre(fret = 7, strings = 0..5, finger = Finger.Index),
                Position(fret = 9, string = 2, finger = Finger.Pinky),
                Position(fret = 9, string = 1, finger = Finger.Ring),
            )
        )
    }
}
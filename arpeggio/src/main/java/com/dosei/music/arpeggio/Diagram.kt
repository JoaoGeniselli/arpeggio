package com.dosei.music.arpeggio

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val gridColor = Color.Black
val nameTextColor = gridColor
val positionColor = Color.Black
val fingerIndicatorColor = Color.White

val nameFontSize = 40.sp
val positionSize = 40.dp
val strokeWidth = 2.dp

const val frets = 5
const val strings = 6

@Composable
fun Diagram(
    modifier: Modifier = Modifier,
    name: String,
    components: List<Component>
) {
    Row(modifier = modifier) {
//        val firstFret = components.firstFret()
//        val lastFret = components.lastFret()
//        if (firstFret > 0 && lastFret > frets) {
//            Text(
//                text = name,
//                fontSize = nameFontSize,
//                color = nameTextColor,
//                fontWeight = FontWeight.Bold
//            )
//        } else {
//            Spacer(modifier = Modifier.width(40.dp))
//        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontSize = nameFontSize,
                color = nameTextColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            GridTop(modifier = Modifier.padding(top = 8.dp)) {
                components.forEach { draw(it) }
            }
        }
//        Spacer(modifier = Modifier.width(40.dp))
    }
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
            name = "Bm7",
            components = listOf(
                Barre(fret = 2, strings = 1..5, finger = Finger.Index),
                Position(fret = 3, string = 4, finger = Finger.Middle),
                Position(fret = 4, string = 3, finger = Finger.Pinky),
                Position(fret = 4, string = 2, finger = Finger.Ring),
            )
        )
    }
}
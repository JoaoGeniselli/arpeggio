package com.dosei.music.arpeggio.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dosei.music.arpeggio.*
import com.dosei.music.arpeggio.sample.ui.theme.ArpeggioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArpeggioTheme {
                Content()
            }
        }
    }
}

@Composable
fun Content() {
    Surface(color = MaterialTheme.colors.background) {
        DiagramTheme {
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
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArpeggioTheme {
        Content()
    }
}
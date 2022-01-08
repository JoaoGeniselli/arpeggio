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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dosei.music.arpeggio.Barre
import com.dosei.music.arpeggio.Diagram
import com.dosei.music.arpeggio.Finger
import com.dosei.music.arpeggio.Position
import com.dosei.music.arpeggio.sample.ui.theme.ArpeggioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArpeggioTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
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
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArpeggioTheme {
        Greeting("Android")
    }
}
package com.dosei.music.arpeggio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun Rect(
    modifier: Modifier = Modifier,
    color: Color = Color.Black
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(
            color = color,
            size = size
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRect() {
    Rect(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(40.dp),
        color = Color.Red
    )
}
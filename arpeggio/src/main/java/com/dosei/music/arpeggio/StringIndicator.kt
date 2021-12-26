package com.dosei.music.arpeggio

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun StringIndicator(
    modifier: Modifier = Modifier,
    canBePlayed: Boolean,
    color: Color
) {
    if (canBePlayed) {
        EnabledString(
            modifier = modifier,
            color = color
        )
    } else {
        DisabledString(
            modifier = modifier,
            color = color
        )
    }
}

@Composable
private fun EnabledString(modifier: Modifier, color: Color) {
    Image(
        modifier = modifier,
        colorFilter = ColorFilter.tint(color),
        painter = painterResource(id = R.drawable.ic_circle_48),
        contentDescription = "Enabled String Indicator"
    )
}

@Composable
private fun DisabledString(modifier: Modifier, color: Color) {
    Image(
        modifier = modifier,
        colorFilter = ColorFilter.tint(color),
        painter = painterResource(id = R.drawable.ic_cross_48),
        contentDescription = "Disabled String Indicator"
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewEnabledString() {
    StringIndicator(
        canBePlayed = true,
        color = Color.Black
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewDisabledString() {
    StringIndicator(
        canBePlayed = false,
        color = Color.Red
    )
}


package com.dosei.music.arpeggio.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class Typography(
    val name: TextStyle = TextStyle(
        color = Color.Black,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold
    ),
    val firstFretIndicator: TextStyle = TextStyle(
        color = Color.Black,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    val fingerIndicator: TextStyle = TextStyle(color = Color.White, fontSize = 20.sp)
)

internal val LocalTypography = staticCompositionLocalOf { Typography() }

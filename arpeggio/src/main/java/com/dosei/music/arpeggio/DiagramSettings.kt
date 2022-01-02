package com.dosei.music.arpeggio

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class DiagramSettings(
    val frets: Int = 4,
    val strings: Int = 6,
    val initialFret: Int = 0,
    val strokeWidth: Dp = 1.dp,
    val colors: Colors
)
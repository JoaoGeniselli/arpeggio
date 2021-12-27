package com.dosei.music.arpeggio

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Settings(
    val frets: Int,
    val strings: Int,
    val initialFret: Int = 0,
    val strokeWidth: Dp,
    val colors: Colors,
    val positionSize: Dp = 40.dp
)
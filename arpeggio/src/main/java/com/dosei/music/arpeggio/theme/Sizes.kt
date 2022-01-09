package com.dosei.music.arpeggio.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Sizes(
    val position: Dp = 40.dp,
    val strokeWidth: Dp = 1.dp
)

internal val LocalSizes = staticCompositionLocalOf { Sizes() }
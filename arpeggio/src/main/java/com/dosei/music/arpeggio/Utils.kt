package com.dosei.music.arpeggio

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.toPixels() = LocalDensity.current.run { toPx() }

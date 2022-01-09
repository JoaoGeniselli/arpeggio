package com.dosei.music.arpeggio.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

class Colors(
    grid: Color = Color.Black,
    position: Color = Color.Black,
    stringUsageIndicator: Color = Color.Black
) {
    var grid by mutableStateOf(grid, structuralEqualityPolicy())
        internal set
    var position by mutableStateOf(position, structuralEqualityPolicy())
        internal set
    var stringUsageIndicator by mutableStateOf(stringUsageIndicator, structuralEqualityPolicy())
        internal set

    fun copy(
        grid: Color = this.grid,
        position: Color = this.position,
        stringUsageIndicator: Color = this.stringUsageIndicator
    ) = Colors(
        grid,
        position,
        stringUsageIndicator
    )
}

internal fun Colors.updateColorsFrom(other: Colors) {
    this.grid = other.grid
    this.position = other.position
    this.stringUsageIndicator = other.stringUsageIndicator
}

internal val LocalColors = staticCompositionLocalOf { Colors() }

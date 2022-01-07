package com.dosei.music.arpeggio

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

data class Geometry(
    val fretSpaceHeight: Float,
    val stringSpaceWidth: Float,
    val canvasSize: Size,
    val inset: Float,
    val positionSize: Float,
    val gridSize: Size,
    val gridStart: Offset,
    val gridEnd: Offset,
    val bottomInset: Float,
    val playIndicatorPadding: Float,
    val playIndicatorSize: Float
) {
    fun centerOfFret(fret: Int): Float {
        val centerDiff = fretSpaceHeight / 2f
        return fretSpaceHeight * fret - centerDiff + inset
    }

    fun centerOfString(string: Int): Float {
        return stringSpaceWidth * string + inset
    }
}
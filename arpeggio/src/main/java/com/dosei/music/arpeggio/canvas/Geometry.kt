package com.dosei.music.arpeggio.canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

internal data class Geometry(
    val strokeWidth: Float,
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

    fun centerOfStringIndicator(string: Int): Offset {
        return Offset(
            x = centerOfString(string),
            y = canvasSize.height - positionSize / 2f
        )
    }

    fun topLeftOfStringIndicator(string: Int): Offset {
        return Offset(
            x = centerOfString(string) - positionSize / 2f + strokeWidth,
            y = canvasSize.height - positionSize + strokeWidth
        )
    }

    fun bottomRightOfStringIndicator(string: Int): Offset {
        return Offset(
            x = centerOfString(string) + positionSize / 2f - strokeWidth,
            y = canvasSize.height - strokeWidth
        )
    }
}
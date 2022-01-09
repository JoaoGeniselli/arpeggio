package com.dosei.music.arpeggio.grid

import androidx.compose.ui.graphics.drawscope.DrawScope
import com.dosei.music.arpeggio.Barre
import com.dosei.music.arpeggio.Component
import com.dosei.music.arpeggio.OpenString
import com.dosei.music.arpeggio.Position
import com.dosei.music.arpeggio.canvas.*
import com.dosei.music.arpeggio.theme.Colors
import com.dosei.music.arpeggio.theme.Sizes
import com.dosei.music.arpeggio.theme.Typography

internal class GridScope(
    initialFret: Int,
    private val colors: Colors,
    private val geometry: Geometry,
    private val sizes: Sizes,
    private val typography: Typography,
    private val strings: Int,
    private val drawScope: DrawScope
) {
    private val stringUsage = (0 until strings).map { it to false }.toMap().toMutableMap()
    private val fretDiff = initialFret.dec()

    fun commit() {
        stringUsage.forEach { entry ->
            val string = entry.key
            val isUsed = entry.value
            if (isUsed) {
                drawScope.run {
                    drawOpenStringIndicator(
                        geometry.centerOfStringIndicator(string),
                        colors.stringUsageIndicator,
                        sizes.strokeWidth,
                        sizes.position
                    )
                }
            } else {
                drawScope.run {
                    drawClosedStringIndicator(
                        geometry.topLeftOfStringIndicator(string),
                        geometry.bottomRightOfStringIndicator(string),
                        colors.stringUsageIndicator,
                        sizes.strokeWidth
                    )
                }
            }
        }
    }

    fun draw(component: Component) {
        when (component) {
            is Barre -> draw(barre = component)
            is OpenString -> draw(openString = component)
            is Position -> draw(position = component)
        }
    }

    fun draw(barre: Barre) {
        drawScope.drawBarre(
            initialStringCenter = geometry.centerOfString(barre.strings.last.toCanvasPosition()),
            finalStringCenter = geometry.centerOfString(barre.strings.first.toCanvasPosition()),
            fretCenter = geometry.centerOfFret(barre.fret.relativeToInitialFret()),
            color = colors.position,
            positionSize = sizes.position
        )

        barre.finger?.let {
            drawScope.drawFingerIndicator(
                finger = it,
                fretCenter = geometry.centerOfFret(barre.fret.relativeToInitialFret()),
                stringCenter = geometry.centerOfString(barre.strings.last.toCanvasPosition()),
                color = typography.fingerIndicator.color,
                positionSize = sizes.position,
                textSize = typography.fingerIndicator.fontSize
            )
        }
        stringUsage.putAll(barre.strings.map { it.toCanvasPosition() to true })
    }

    fun draw(position: Position) {
        val fretCenter = geometry.centerOfFret(position.fret.relativeToInitialFret())
        val stringCenter = geometry.centerOfString(position.string.toCanvasPosition())

        drawScope.drawPosition(
            fretCenter = fretCenter,
            stringLine = stringCenter,
            color = colors.position,
            positionSize = sizes.position
        )

        position.finger?.let {
            drawScope.drawFingerIndicator(
                finger = it,
                fretCenter = fretCenter,
                stringCenter = stringCenter,
                color = typography.fingerIndicator.color,
                positionSize = sizes.position,
                textSize = typography.fingerIndicator.fontSize
            )
        }
        stringUsage[position.string.toCanvasPosition()] = true
    }

    fun draw(openString: OpenString) {
        stringUsage[openString.string.toCanvasPosition()] = true
    }

    private fun Int.relativeToInitialFret(): Int = minus(fretDiff)

    private fun Int.toCanvasPosition(): Int = strings - this
}

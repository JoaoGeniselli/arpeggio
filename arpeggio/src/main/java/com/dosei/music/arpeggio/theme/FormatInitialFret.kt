package com.dosei.music.arpeggio.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

interface FormatInitialFret {
    operator fun invoke(fret: Int): AnnotatedString

    companion object {

        fun enUs(): FormatInitialFret = EnUsFormatInitialFret()
        fun ptBr(): FormatInitialFret = PtBrFormatInitialFret()
    }
}

class EnUsFormatInitialFret : FormatInitialFret {

    override fun invoke(fret: Int): AnnotatedString =
        buildAnnotatedString {
            append(fret.toString())
            withStyle(style = SpanStyle(baselineShift = BaselineShift.Superscript)) {
                val ordinal = when (fret) {
                    1 -> "st"
                    2 -> "nd"
                    3 -> "rd"
                    else -> "th"
                }
                append(ordinal)
            }
        }
}

class PtBrFormatInitialFret : FormatInitialFret {

    override fun invoke(fret: Int): AnnotatedString =
        buildAnnotatedString {
            append(fret.toString())
            withStyle(
                style = SpanStyle(
                    baselineShift = BaselineShift.Superscript,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("a")
            }
        }
}

internal val LocalFormatInitialFret: ProvidableCompositionLocal<FormatInitialFret> =
    staticCompositionLocalOf { EnUsFormatInitialFret() }
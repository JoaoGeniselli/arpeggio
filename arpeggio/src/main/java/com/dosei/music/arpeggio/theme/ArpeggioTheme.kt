package com.dosei.music.arpeggio.theme

import androidx.compose.runtime.*
import com.dosei.music.arpeggio.DefaultFrets
import com.dosei.music.arpeggio.DefaultInitialFret
import com.dosei.music.arpeggio.DefaultStrings

@Composable
fun ArpeggioTheme(
    frets: Int = DefaultFrets,
    strings: Int = DefaultStrings,
    colors: Colors = Colors(),
    typography: Typography = Typography(),
    sizes: Sizes = Sizes(),
    formatInitialFret: FormatInitialFret = FormatInitialFret.enUs(),
    content: @Composable () -> Unit
) {
    val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }
    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalTypography provides typography.copy(),
        LocalSizes provides sizes.copy(),
        LocalFormatInitialFret provides formatInitialFret,
        LocalFrets provides frets,
        LocalStrings provides strings,
        content = content
    )
}

internal val LocalFrets = staticCompositionLocalOf { DefaultFrets }
internal val LocalStrings = staticCompositionLocalOf { DefaultStrings }

object DiagramTheme {

    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val sizes: Sizes
        @Composable
        @ReadOnlyComposable
        get() = LocalSizes.current

    val formatInitialFret: FormatInitialFret
        @Composable
        @ReadOnlyComposable
        get() = LocalFormatInitialFret.current

    val frets: Int
        @Composable
        @ReadOnlyComposable
        get() = LocalFrets.current

    val strings: Int
        @Composable
        @ReadOnlyComposable
        get() = LocalStrings.current

    val initialFretRange: IntRange
        @Composable
        @ReadOnlyComposable
        get() = DefaultInitialFret..frets
}
package com.dosei.music.arpeggio

sealed class Component

data class Barre(
    val fret: Int,
    val strings: IntRange,
    val finger: Finger? = null
) : Component()

data class Position(
    val fret: Int,
    val string: Int,
    val finger: Finger? = null
) : Component()

data class OpenString(val string: Int) : Component()

internal fun List<Component>.fitsInFretRange(fretRange: IntRange): Boolean =
    fretRange.run {
        contains(firstFret()) and contains(lastFret())
    }

internal fun List<Component>.firstFret(): Int =
    minOf {
        when (it) {
            is Barre -> it.fret
            is Position -> it.fret
            else -> Int.MAX_VALUE
        }
    }

internal fun List<Component>.lastFret(): Int =
    maxOf {
        when (it) {
            is Barre -> it.fret
            is Position -> it.fret
            else -> 0
        }
    }
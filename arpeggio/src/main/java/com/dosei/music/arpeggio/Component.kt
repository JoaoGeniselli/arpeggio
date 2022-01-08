package com.dosei.music.arpeggio

sealed class Component

class Barre(
    val fret: Int,
    val strings: IntRange,
    val finger: Finger? = null
) : Component()

class Position(
    val fret: Int,
    val string: Int,
    val finger: Finger? = null
) : Component()

class OpenString(val string: Int) : Component()
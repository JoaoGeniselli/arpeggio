package com.dosei.music.arpeggio

data class Position(
    val string: Int,
    val fret: Int,
    val finger: Finger?,
    val barre: IntRange? = null
) {

    companion object {
        fun openString(string: Int) = Position(
            string = string,
            fret = 0,
            finger = null,
            barre = null
        )
    }
}
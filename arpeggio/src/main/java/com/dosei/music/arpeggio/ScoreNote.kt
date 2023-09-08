package com.dosei.music.arpeggio

enum class ScoreNote(
    val index: Int,
    val isMainLine: Boolean,
    val isLine: Boolean,
    val tailInStart: Boolean,
    val tailIndex: Int,
    val supplementaryLines: List<Int>
) {
    D6(index = 0,   isMainLine = false,  isLine = true,   tailInStart = true,   tailIndex = 16, supplementaryLines = listOf(10, 8, 6, 4, 2, 0)),
    C6(index = 1,   isMainLine = false,  isLine = false,  tailInStart = true,   tailIndex = 16, supplementaryLines = listOf(10, 8, 6, 4, 2)),
    B5(index = 2,   isMainLine = false,  isLine = true,   tailInStart = true,   tailIndex = 16, supplementaryLines = listOf(10, 8, 6, 4, 2)),
    A5(index = 3,   isMainLine = false,  isLine = false,  tailInStart = true,   tailIndex = 16, supplementaryLines = listOf(10, 8, 6, 4)),
    G5(index = 4,   isMainLine = false,  isLine = true,   tailInStart = true,   tailIndex = 16, supplementaryLines = listOf(10, 8, 6, 4)),
    F5(index = 5,   isMainLine = false,  isLine = false,  tailInStart = true,   tailIndex = 16, supplementaryLines = listOf(10, 8, 6)),
    E5(index = 6,   isMainLine = false,  isLine = true,   tailInStart = true,   tailIndex = 16, supplementaryLines = listOf(10, 8, 6)),
    D5(index = 7,   isMainLine = false,  isLine = false,  tailInStart = true,   tailIndex = 16, supplementaryLines = listOf(10, 8)),
    C5(index = 8,   isMainLine = false,  isLine = true,   tailInStart = true,   tailIndex = 16, supplementaryLines = listOf(10, 8)),
    B4(index = 9,   isMainLine = false,  isLine = false,  tailInStart = true,   tailIndex = 16, supplementaryLines = listOf(10)),
    A4(index = 10,  isMainLine = false,  isLine = true,   tailInStart = true,   tailIndex = 17, supplementaryLines = listOf(10)),
    G4(index = 11,  isMainLine = false,  isLine = false,  tailInStart = true,   tailIndex = 18, supplementaryLines = listOf()),
    F4(index = 12,  isMainLine = true,   isLine = true,   tailInStart = true,   tailIndex = 19, supplementaryLines = listOf()),
    E4(index = 13,  isMainLine = false,  isLine = false,  tailInStart = true,   tailIndex = 20, supplementaryLines = listOf()),
    D4(index = 14,  isMainLine = true,   isLine = true,   tailInStart = true,   tailIndex = 21, supplementaryLines = listOf()),
    C4(index = 15,  isMainLine = false,  isLine = false,  tailInStart = true,   tailIndex = 22, supplementaryLines = listOf()),
    B3(index = 16,  isMainLine = true,   isLine = true,   tailInStart = true,   tailIndex = 23, supplementaryLines = listOf()),
    A3(index = 17,  isMainLine = false,  isLine = false,  tailInStart = false,  tailIndex = 10, supplementaryLines = listOf()),
    G3(index = 18,  isMainLine = true,   isLine = true,   tailInStart = false,  tailIndex = 11, supplementaryLines = listOf()),
    F3(index = 19,  isMainLine = false,  isLine = false,  tailInStart = false,  tailIndex = 12, supplementaryLines = listOf()),
    E3(index = 20,  isMainLine = true,   isLine = true,   tailInStart = false,  tailIndex = 13, supplementaryLines = listOf()),
    D3(index = 21,  isMainLine = false,  isLine = false,  tailInStart = false,  tailIndex = 14, supplementaryLines = listOf()),
    C3(index = 22,  isMainLine = false,  isLine = true,   tailInStart = false,  tailIndex = 15, supplementaryLines = listOf(22)),
    B2(index = 23,  isMainLine = false,  isLine = false,  tailInStart = false,  tailIndex = 16, supplementaryLines = listOf(22)),
    A2(index = 24,  isMainLine = false,  isLine = true,   tailInStart = false,  tailIndex = 16, supplementaryLines = listOf(24, 22)),
    G2(index = 25,  isMainLine = false,  isLine = false,  tailInStart = false,  tailIndex = 16, supplementaryLines = listOf(24, 22)),
    F2(index = 26,  isMainLine = false,  isLine = true,   tailInStart = false,  tailIndex = 16, supplementaryLines = listOf(26, 24, 22)),
    E2(index = 27,  isMainLine = false,  isLine = false,  tailInStart = false,  tailIndex = 16, supplementaryLines = listOf(26, 24, 22));

    companion object {

        fun getAll(intRange: IntRange): List<ScoreNote> {
            return values().filter { intRange.contains(it.index) }.sortedBy { it.index }
        }

        fun getByIndex(noteIndex: Int): ScoreNote? =
            values().firstOrNull { it.index == noteIndex }
    }
}
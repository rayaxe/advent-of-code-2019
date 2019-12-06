package com.github.rayaxe.days

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day5Tests {

    @Nested
    inner class Day5Part2 {

        @Test
        fun equalToPositionMode() {
            val program = listOf(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8)
            day5Part1(program, 8)
        }

        @Test
        fun lessThanPositionMode() {
            val program = listOf(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8)
            day5Part1(program, 8)
        }

        @Test
        fun equalToImmediateMode() {
            val program = listOf(3, 3, 1108, -1, 8, 3, 4, 3, 99)
            day5Part1(program, 8)
        }

        @Test
        fun lessThanImmediateMode() {
            val program = listOf(3, 3, 1107, -1, 8, 3, 4, 3, 99)
            day5Part1(program, 8)
        }

        @Test
        fun jumpPositionMode() {
            val program = listOf(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9)
            day5Part1(program, 8)
        }

        @Test
        fun jumpImmediateMode() {
            val program = listOf(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1)
            day5Part1(program, 8)
        }

        @Test
        fun largeExample() {
            val program = listOf(
                3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
                1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
                999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99
            )
            day5Part1(program, 8)
        }
    }
}
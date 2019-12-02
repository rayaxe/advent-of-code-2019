package com.github.rayaxe.days

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day2Tests {

    @Nested
    inner class Day2Part1 {

        @Test
        fun example1() {
            assertEquals(listOf(2, 0, 0, 0, 99), day2Part1(mutableListOf(1, 0, 0, 0, 99)))
        }

        @Test
        fun example2() {
            assertEquals(listOf(2, 3, 0, 6, 99), day2Part1(mutableListOf(2, 3, 0, 3, 99)))
        }

        @Test
        fun example3() {
            assertEquals(listOf(2, 4, 4, 5, 99, 9801), day2Part1(mutableListOf(2, 4, 4, 5, 99, 0)))
        }

        @Test
        fun example4() {
            assertEquals(listOf(30, 1, 1, 4, 2, 5, 6, 0, 99), day2Part1(mutableListOf(1, 1, 1, 4, 99, 5, 6, 0, 99)))
        }
    }
}
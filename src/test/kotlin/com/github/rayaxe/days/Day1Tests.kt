package com.github.rayaxe.days

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day1Tests {

    @Nested
    inner class Day1Part1 {

        @Test
        fun example1() {
            assertEquals(2, day1Part1(listOf("12")))
        }

        @Test
        fun example2() {
            assertEquals(2, day1Part1(listOf("14")))
        }

        @Test
        fun example3() {
            assertEquals(654, day1Part1(listOf("1969")))
        }

        @Test
        fun example4() {
            assertEquals(33583, day1Part1(listOf("100756")))
        }
    }

    @Nested
    inner class Day1Part2 {

        @Test
        fun example1() {
            assertEquals(2, day1Part2(listOf("14")))
        }

        @Test
        fun example2() {
            assertEquals(966, day1Part2(listOf("1969")))
        }

        @Test
        fun example3() {
            assertEquals(50346, day1Part2(listOf("100756")))
        }
    }
}
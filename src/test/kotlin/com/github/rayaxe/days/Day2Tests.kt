package com.github.rayaxe.days

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day2Tests {

    @Nested
    inner class Day2Part1 {

        @Test
        fun example1() {
            val program = listOf(1L, 0, 0, 0, 99)
            assertEquals(listOf(2L, 0, 0, 0, 99), day2Part1(program, program[1], program[2]))
        }

        @Test
        fun example2() {
            val program = listOf(2L, 3, 0, 3, 99)
            assertEquals(listOf(2L, 3, 0, 6, 99), day2Part1(program, program[1], program[2]))
        }

        @Test
        fun example3() {
            val program = listOf(2L, 4, 4, 5, 99, 0)
            assertEquals(listOf(2L, 4, 4, 5, 99, 9801), day2Part1(program, program[1], program[2]))
        }

        @Test
        fun example4() {
            val program = listOf(1L, 1, 1, 4, 99, 5, 6, 0, 99)
            assertEquals(listOf(30L, 1, 1, 4, 2, 5, 6, 0, 99), day2Part1(program, program[1], program[2]))
        }
    }
}
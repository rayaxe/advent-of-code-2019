package com.github.rayaxe.days

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day9Tests {

    @Nested
    inner class Day9Part1 {

        @Test
        fun example1() {
            val program = listOf(109L, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99)
            assertEquals(program, day9(program, 1, false))
        }

        @Test
        fun example2() {
            val program = listOf(1102L, 34915192, 34915192, 7, 4, 7, 99, 0)
            assertEquals(listOf(1219070632396864), day9(program, 1, false))
        }

        @Test
        fun example3() {
            val program = listOf(104L, 1125899906842624, 99)
            assertEquals(listOf(1125899906842624), day9(program, 1, false))
        }
    }
}

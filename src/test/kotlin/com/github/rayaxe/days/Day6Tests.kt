package com.github.rayaxe.days

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day6Tests {

    @Nested
    inner class Day6Part1 {

        @Test
        fun example1() {
            val orbits = listOf("COM)B", "B)C", "C)D", "D)E", "E)F", "B)G", "G)H", "D)I", "E)J", "J)K", "K)L")
            assertEquals(42, day6Part1(orbits))
        }
    }
}
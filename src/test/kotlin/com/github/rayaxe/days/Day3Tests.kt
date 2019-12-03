package com.github.rayaxe.days

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day3Tests {

    @Nested
    inner class Day3Part1 {

        @Test
        fun example1() {
            val wire1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72".split(",")
            val wire2 = "U62,R66,U55,R34,D71,R55,D58,R833".split(",")
            assertEquals(159, day3Part1(wire1, wire2))
        }

        @Test
        fun example2() {
            val wire1 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51".split(",")
            val wire2 = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7".split(",")
            assertEquals(135, day3Part1(wire1, wire2))
        }
    }

    @Nested
    inner class Day3Part2 {

        @Test
        fun example1() {
            val wire1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72".split(",")
            val wire2 = "U62,R66,U55,R34,D71,R55,D58,R833".split(",")
            assertEquals(610, day3Part2(wire1, wire2))
        }

        @Test
        fun example2() {
            val wire1 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51".split(",")
            val wire2 = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7".split(",")
            assertEquals(410, day3Part2(wire1, wire2))
        }
    }
}
package com.github.rayaxe.days

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.test.assertEquals

internal class Day10Tests {

    @Nested
    inner class Day10Part1 {

        @Test
        fun example1() {
            val asteroids = listOf(
                ".#..#",
                ".....",
                "#####",
                "....#",
                "...##"
            )
            assertEquals(8, day10Part1(asteroids))
        }

        @Test
        fun example2() {
            val asteroids = listOf(
                "......#.#.",
                "#..#.#....",
                "..#######.",
                ".#.#.###..",
                ".#..#.....",
                "..#....#.#",
                "#..#....#.",
                ".##.#..###",
                "##...#..#.",
                ".#....####"
            )
            assertEquals(33, day10Part1(asteroids))
        }

        @Test
        fun example3() {
            val asteroids = listOf(
                "#.#...#.#.",
                ".###....#.",
                ".#....#...",
                "##.#.#.#.#",
                "....#.#.#.",
                ".##..###.#",
                "..#...##..",
                "..##....##",
                "......#...",
                ".####.###."
            )
            assertEquals(35, day10Part1(asteroids))
        }

        @Test
        fun example4() {
            val asteroids = listOf(
                ".#..#..###",
                "####.###.#",
                "....###.#.",
                "..###.##.#",
                "##.##.#.#.",
                "....###..#",
                "..#.#..#.#",
                "#..#.#.###",
                ".##...##.#",
                ".....#.#.."
            )
            assertEquals(41, day10Part1(asteroids))
        }

        @Test
        fun example5() {
            val asteroids = listOf(
                ".#..##.###...#######",
                "##.############..##.",
                ".#.######.########.#",
                ".###.#######.####.#.",
                "#####.##.#.##.###.##",
                "..#####..#.#########",
                "####################",
                "#.####....###.#.#.##",
                "##.#################",
                "#####.##.###..####..",
                "..######..##.#######",
                "####.##.####...##..#",
                ".#####..#.######.###",
                "##...#.##########...",
                "#.##########.#######",
                ".####.#.###.###.#.##",
                "....##.##.###..#####",
                ".#.#.###########.###",
                "#.#.#.#####.####.###",
                "###.##.####.##.#..##"
            )
            assertEquals(210, day10Part1(asteroids))
        }

        @Test
        fun test() {
            val origin = Location(0, 0)
            val vectorA = vector(Location(-2, -2), Location(5, 5))
            val magnitudeA = distance(origin, vectorA)
            println("x=" + vectorA.x / magnitudeA + ", y=" + vectorA.y / magnitudeA)
            val vectorB = vector(Location(-2, -2), Location(11, 10))
            val magnitudeB = distance(origin, vectorB)
            println("x=" + vectorB.x / magnitudeB + ", y=" + vectorB.y / magnitudeB)
            println(vector(Location(5, 5), Location(-5, -5)))
            println(vector(Location(5, 5), Location(-10, -10)))
        }

        fun vector(a: Location, b: Location): Location {
            return Location(b.x - a.x, b.y - a.y)
        }

        private fun distance(a: Location, b: Location): Double {
            return sqrt((a.x - b.x).toDouble().pow(2) + (a.y - b.y).toDouble().pow(2))
        }
    }
}

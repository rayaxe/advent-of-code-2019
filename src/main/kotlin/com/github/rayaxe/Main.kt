package com.github.rayaxe

import com.github.rayaxe.days.*
import java.io.File

fun main() {
    // Day 1
//    val inputDay1 = readLinesFromFile("input_day1.txt")
//    println(day1Part1(inputDay1))
//    println(day1Part2(inputDay1))
    // Day 2
//    val inputDay2 = readInstructions("input_day2.txt")
//    println(day2Part1(inputDay2, 12, 2))
//    println(day2Part2(inputDay2))
    // Day 3
//    val inputDay3 = readLinesFromFile("input_day3.txt")
//    println(day3Part1(inputDay3[0].split(","), inputDay3[1].split(",")))
//    println(day3Part2(inputDay3[0].split(","), inputDay3[1].split(",")))
    // Day 4
//    println(day4Part1(123257..647015))
//    println(day4Part2(123257..647015))
    // Day 5
//    val inputDay5 = readInstructions("input_day5.txt")
//    println(day5(inputDay5, 1))
//    println(day5(inputDay5, 5))
    // Day 6
//    val inputDay6 = readLinesFromFile("input_day6.txt")
//    println(day6Part1(inputDay6))
//    println(day6Part2(inputDay6))
    // Day 7
//    val inputDay7 = readInstructions("input_day7.txt")
//    println(day7Part1(inputDay7))
//    println(day7Part2(inputDay7))
    // Day 8
//    val inputDay8 = readLinesFromFile("input_day8.txt")[0]
//    println(day8Part1(inputDay8))
//    println(day8Part2(inputDay8))
    // Day 9
//    val inputDay9 = readInstructions("input_day9.txt")
//    println(day9(inputDay9, 1)[0])
//    println(day9(inputDay9, 2)[0])
    // Day 10
//    val inputDay10 = readLinesFromFile("input_day10.txt")
//    println(day10Part1(inputDay10))
    // Day 11
//    val inputDay11 = readInstructions("input_day11.txt")
//    println(day11(inputDay11, 0))
//    println(day11(inputDay11, 1))
    // Day 12
//    val inputDay12 = readLinesFromFile("input_day12.txt")
//    println(day12Part1(inputDay12, 1000L))
//    println(day12Part2(inputDay12))
    // Day 13
    val inputDay13 = readInstructions("input_day13.txt")
    println(day13Part1(inputDay13))
    println(day13Part2(inputDay13))
}

private fun readLinesFromFile(filename: String) = File("src/main/resources/days/$filename").readLines()

private fun readInstructions(filename: String) = readLinesFromFile(filename)[0].split(",").map { it.toLong() }

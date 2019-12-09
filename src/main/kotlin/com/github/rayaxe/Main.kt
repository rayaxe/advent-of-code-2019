package com.github.rayaxe

import com.github.rayaxe.days.*
import java.io.File

fun main() {
    // Day 1
//    val inputDay1 = readLinesFromFile("input_day1.txt")
//    println(day1Part1(inputDay1))
//    println(day1Part2(inputDay1))
    // Day 2
//    val inputDay2 = readLinesFromFile("input_day2.txt")[0].split(",").map { it.toInt() }
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
//    val inputDay5 = readLinesFromFile("input_day5.txt")[0].split(",").map { it.toLong() }
//    day5Part1(inputDay5, 1)
//    day5Part1(inputDay5, 5)
    // Day 6
//    val inputDay6 = readLinesFromFile("input_day6.txt")
//    println(day6Part1(inputDay6))
//    println(day6Part2(inputDay6))
    // Day 7
//    val inputDay7 = readLinesFromFile("input_day7.txt")[0].split(",").map { it.toLong() }
//    println(day7Part1(inputDay7))
//    println(day7Part2(inputDay7))
    // Day 8
//    val inputDay8 = readLinesFromFile("input_day8.txt")[0]
//    println(day8Part1(inputDay8))
//    println(day8Part2(inputDay8))
    // Day 9
    val inputDay9 = readLinesFromFile("input_day9.txt")[0].split(",").map { it.toLong() }
//    println(day9(inputDay9, 1))
    println(day9(inputDay9, 2))
}

private fun readLinesFromFile(filename: String) = File("src/main/resources/days/$filename").readLines()

package com.github.rayaxe

import com.github.rayaxe.days.*
import java.io.File

fun main(args: Array<String>) {
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
//    val inputDay5 = readLinesFromFile("input_day5.txt")[0].split(",").map { it.toInt() }
//    day5Part1(inputDay5, 1)
//    day5Part1(inputDay5, 5)
    // Day 6
    val inputDay6 = readLinesFromFile("input_day6.txt")
//    println(day6Part1(inputDay6))
    println(day6Part2(inputDay6))
}

private fun readLinesFromFile(filename: String) = File("src/main/resources/days/" + filename).readLines()

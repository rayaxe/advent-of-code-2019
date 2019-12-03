package com.github.rayaxe

import com.github.rayaxe.days.*
import java.io.File

fun main(args: Array<String>) {
    // Day 1
    val inputDay1 = readLinesFromFile("input_day1.txt")
//    println(day1Part1(inputDay1))
//    println(day1Part2(inputDay1))
    // Day 2
    val inputDay2 = readLinesFromFile("input_day2.txt")[0].split(",").map { it.toInt() }
//    println(day2Part1(inputDay2, 12, 2))
//    println(day2Part2(inputDay2))
    val inputDay3 = readLinesFromFile("input_day3.txt")
//    println(day3Part1(inputDay3[0].split(","), inputDay3[1].split(",")))
    println(day3Part2(inputDay3[0].split(","), inputDay3[1].split(",")))
}

private fun readLinesFromFile(filename: String) = File("src/main/resources/days/" + filename).readLines()

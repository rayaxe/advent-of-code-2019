package com.github.rayaxe

import com.github.rayaxe.days.*
import java.io.File

fun main(args: Array<String>) {
    // Day 1
    val inputDay1 = readLinesFromFile("input_day1.txt")
//    println(day1Part1(inputDay1))
//    println(day1Part2(inputDay1))
    // Day 2
    val inputDay2 = readLinesFromFile("input_day2.txt")[0].split(",")
    val program = inputDay2.map { it.toInt() }.toMutableList()
    program[1] = 12
    program[2] = 2
    println(day2Part1(program))
}

private fun readLinesFromFile(filename: String) = File("src/main/resources/days/" + filename).readLines()

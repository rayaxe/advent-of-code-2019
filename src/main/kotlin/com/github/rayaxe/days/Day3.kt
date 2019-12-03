package com.github.rayaxe.days

import kotlin.math.abs

data class Coordinate(val x: Int, val y: Int) : Comparable<Coordinate> {
    var steps: Int = 0

    override fun compareTo(other: Coordinate): Int = when {
        x == other.x -> when { y == other.y -> 0; y < other.y -> -1; else -> 1}
        x < other.x -> -1
        else -> 1
    }

    override fun toString(): String {
        return "Coordinate(x=$x, y=$y, steps=$steps)"
    }

}

fun day3Part1(path1: List<String>, path2: List<String>): Int? {
    val wire1: Set<Coordinate> = trace(path1)
    val wire2: Set<Coordinate> = trace(path2)
    val centralPort = Coordinate(0, 0)
    return wire1.intersect(wire2).map { manhattanDistance(it, centralPort) }.min()
}

fun day3Part2(path1: List<String>, path2: List<String>): Int? {
    val wire1: MutableSet<Coordinate> = trace(path1).toMutableSet()
    val wire2: MutableSet<Coordinate> = trace(path2).toMutableSet()
    wire1.retainAll(wire2)
    wire2.retainAll(wire1)
    return wire1.sorted().zip(wire2.sorted()).map { it.first.steps + it.second.steps }.min()
}

fun trace(path: List<String>): Set<Coordinate> {
    val result = mutableSetOf<Coordinate>()
    var position = Coordinate(0, 0)
    for (instruction in path) {
        val direction = instruction[0]
        val steps = instruction.substring(1).toInt()
        for (step in 1..steps) {
            val prevSteps = position.steps
            position = when (direction) {
                'U' -> Coordinate(position.x, position.y + 1)
                'D' -> Coordinate(position.x, position.y - 1)
                'L' -> Coordinate(position.x - 1, position.y)
                'R' -> Coordinate(position.x + 1, position.y)
                else -> throw IllegalStateException("Unrecognized direction: $direction")
            }
            position.steps = prevSteps + 1
            result.add(position)
        }
    }
    return result
}

private fun manhattanDistance(a: Coordinate, b: Coordinate): Int {
    return abs(a.x - b.x) + abs(a.y - b.y)
}

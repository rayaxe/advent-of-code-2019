package com.github.rayaxe.days

import kotlin.math.pow
import kotlin.math.sqrt

fun day10Part1(asteroidsGrid: List<String>): Int {
    val maxY = asteroidsGrid.size
    val maxX = asteroidsGrid[0].length
    val asteroids = mutableListOf<Location>()
    for (y in 0 until maxY) {
        for (x in 0 until maxX) {
            if (asteroidsGrid[y][x] == '#') {
                asteroids.add(Location(x, y))
            }
        }
    }
    for (source in asteroids) {
        for (target in asteroids) {
            if (source == target) {
                continue
            }
            var isBlocked = false
            for (blocker in asteroids) {
                if (source == blocker || target == blocker) {
                    continue
                }
                if (targetIsBlocked(source, blocker, target)) {
                    isBlocked = true
                    break
                }
            }
            if (!isBlocked) {
                source.count += 1
            }
        }
    }
    val maxBy = asteroids.maxBy { it.count }!!
    println(maxBy)
    return maxBy.count
}

// TODO use angle calculations :)
fun targetIsBlocked(source: Location, blocker: Location, target: Location): Boolean {
    val ab = distance(source, blocker)
    val bc = distance(target, blocker)
    val ac = distance(source, target)
    return ab + bc == ac
}

private fun distance(a: Location, b: Location): Double {
    return sqrt((a.x - b.x).toDouble().pow(2) + (a.y - b.y).toDouble().pow(2))
}

data class Location(val x: Int, val y: Int) {
    var count: Int = 0
    override fun toString(): String {
        return "Location(x=$x, y=$y, count=$count)"
    }
}

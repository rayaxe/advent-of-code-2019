package com.github.rayaxe.days

fun day1Part1(masses: List<String>): Int {
    return masses.stream().mapToInt { mass -> calcFuel(mass.toInt()) }.sum()
}

private fun calcFuel(mass: Int) = Math.floorDiv(mass, 3) - 2

fun day1Part2(masses: List<String>): Int {
    return masses.stream().mapToInt { mass -> recursivelyCalcFuel(mass.toInt())}.sum()
}

private fun recursivelyCalcFuel(mass: Int): Int {
    val fuel = calcFuel(mass)
    return if (fuel > 0) fuel + recursivelyCalcFuel(fuel) else 0
}

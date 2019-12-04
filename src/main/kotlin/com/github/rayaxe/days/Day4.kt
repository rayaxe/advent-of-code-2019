package com.github.rayaxe.days

fun day4Part1(range: IntRange): Int {
    return range.map { it.toString() }.filter { isIncreasing(it) && hasDouble(it) }.count()
}

fun day4Part2(range: IntRange): Int {
    return range.map { it.toString() }.filter { isIncreasing(it) && hasNonRepeatingDouble(it) }.count()
}

private fun isIncreasing(digits: String): Boolean {
    return digits[0] <= digits[1]
            && digits[1] <= digits[2]
            && digits[2] <= digits[3]
            && digits[3] <= digits[4]
            && digits[4] <= digits[5]
}

private fun hasDouble(digits: String): Boolean {
    return digits[0] == digits[1]
            || digits[1] == digits[2]
            || digits[2] == digits[3]
            || digits[3] == digits[4]
            || digits[4] == digits[5]
}

private fun hasNonRepeatingDouble(digits: String): Boolean {
    return (digits[0] == digits[1] && digits[1] != digits[2])
            || (digits[0] != digits[1] && digits[1] == digits[2] && digits[2] != digits[3])
            || (digits[1] != digits[2] && digits[2] == digits[3] && digits[3] != digits[4])
            || (digits[2] != digits[3] && digits[3] == digits[4] && digits[4] != digits[5])
            || (digits[3] != digits[4] && digits[4] == digits[5])
}

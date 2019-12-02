package com.github.rayaxe.days

import kotlin.reflect.KFunction2

fun day2Part1(program: MutableList<Int>): List<Int> {
    return run(program, 0)
}

private fun run(program: MutableList<Int>, pos: Int): List<Int> {
    return when (val opcode = program[pos]) {
        1 -> calc(program, pos, Math::addExact)
        2 -> calc(program, pos, Math::multiplyExact)
        99 -> program
        else -> throw IllegalStateException("Unrecognized opcode: $opcode")
    }
}

fun calc(program: MutableList<Int>, pos: Int, operation: KFunction2<Int, Int, Int>): List<Int> {
    program[program[pos + 3]] = operation(program[program[pos + 1]], program[program[pos + 2]])
    return run(program, pos + 4)
}

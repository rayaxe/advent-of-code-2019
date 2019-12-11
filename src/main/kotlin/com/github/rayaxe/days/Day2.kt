package com.github.rayaxe.days

import java.util.*

fun day2Part1(program: List<Long>, noun: Long, verb: Long): List<Long> {
    return start(program.toMutableList(), noun, verb)
}

fun day2Part2(program: List<Long>): Long {
    val memory = LinkedList(program)
    for (noun in 0L..99) {
        for (verb in 0L..99) {
            val output = start(LinkedList(memory), noun, verb)[0]
            if (19690720L == output) {
                return 100L * noun + verb
            }
        }
    }
    throw IllegalStateException("No result!")
}

private fun start(program: MutableList<Long>, noun: Long, verb: Long): List<Long> {
    program[1] = noun
    program[2] = verb
    return run(program, 0)
}

private fun run(program: MutableList<Long>, pointer: Int): List<Long> {
    return when (val opcode = program[pointer].toInt()) {
        1 -> calc(program, pointer) { x, y -> x + y }
        2 -> calc(program, pointer) { x, y -> x * y }
        99 -> program
        else -> throw IllegalStateException("Unrecognized opcode: $opcode")
    }
}

private fun calc(program: MutableList<Long>, pointer: Int, operation: (Long, Long) -> Long): List<Long> {
    program[program[pointer + 3].toInt()] =
        operation(program[program[pointer + 1].toInt()], program[program[pointer + 2].toInt()])
    return run(program, pointer + 4)
}

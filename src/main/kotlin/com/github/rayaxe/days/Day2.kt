package com.github.rayaxe.days

import java.util.*
import kotlin.reflect.KFunction2

fun day2Part1(program: List<Int>, noun: Int, verb: Int): List<Int> {
    return start(program.toMutableList(), noun, verb)
}

fun day2Part2(program: List<Int>): Int {
    val memory = LinkedList(program)
    for (noun in 0..99) {
        for (verb in 0..99) {
            val output = start(LinkedList(memory), noun, verb)[0]
            if (19690720 == output) {
                return 100 * noun + verb
            }
        }
    }
    throw IllegalStateException("No result!")
}

private fun start(program: MutableList<Int>, noun: Int, verb: Int): List<Int> {
    program[1] = noun
    program[2] = verb
    return run(program, 0)
}

private fun run(program: MutableList<Int>, pointer: Int): List<Int> {
    return when (val opcode = program[pointer]) {
        1 -> calc(program, pointer, Math::addExact)
        2 -> calc(program, pointer, Math::multiplyExact)
        99 -> program
        else -> throw IllegalStateException("Unrecognized opcode: $opcode")
    }
}

private fun calc(program: MutableList<Int>, pointer: Int, instruction: KFunction2<Int, Int, Int>): List<Int> {
    program[program[pointer + 3]] = instruction(program[program[pointer + 1]], program[program[pointer + 2]])
    return run(program, pointer + 4)
}

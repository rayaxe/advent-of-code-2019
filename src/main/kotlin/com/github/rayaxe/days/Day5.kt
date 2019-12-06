package com.github.rayaxe.days

import java.lang.IllegalArgumentException
import kotlin.reflect.KFunction2

var systemId = 1
var program = mutableListOf<Int>()
var pointer = 0

fun day5Part1(_program: List<Int>, _systemId: Int) {
    systemId = _systemId
    program = _program.toMutableList()
    run()
}

private fun run() {
    val opCodeAndModes = parseOpCodeAndModes(program[pointer])
    return when (val opCode = opCodeAndModes.opCode) {
        1 -> operation(opCodeAndModes.modes, Math::addExact)
        2 -> operation(opCodeAndModes.modes, Math::multiplyExact)
        3 -> input(systemId)
        4 -> output(opCodeAndModes.modes)
        5 -> jump(opCodeAndModes.modes) { x -> x != 0 }
        6 -> jump(opCodeAndModes.modes) { x -> x == 0 }
        7 -> compare(opCodeAndModes.modes) { x, y -> x < y}
        8 -> compare(opCodeAndModes.modes) { x, y -> x == y}
        99 -> Unit
        else -> throw IllegalStateException("Unrecognized opcode: $opCode")
    }
}

private fun operation(modes: List<ParameterMode>, operation: KFunction2<Int, Int, Int>) {
    val mode1 = modes.getOrElse(0) { ParameterMode.POSITION_MODE }
    val mode2 = modes.getOrElse(1) { ParameterMode.POSITION_MODE }
    val parameter1 = read(pointer + 1, mode1)
    val parameter2 = read(pointer + 2, mode2)
    program[program[pointer + 3]] = operation(parameter1, parameter2)
    pointer += 4
    run()
}

private fun input(input: Int) {
    program[program[pointer + 1]] = input
    pointer += 2
    run()
}

private fun output(modes: List<ParameterMode>) {
    val mode = modes.getOrElse(0) { ParameterMode.POSITION_MODE }
    val parameter = read(pointer + 1, mode)
    println("Output: $parameter")
    pointer += 2
    run()
}

private fun jump(modes: List<ParameterMode>, shouldJump: (Int) -> Boolean) {
    val mode1 = modes.getOrElse(0) { ParameterMode.POSITION_MODE }
    val mode2 = modes.getOrElse(1) { ParameterMode.POSITION_MODE }
    val parameter1 = read(pointer + 1, mode1)
    val parameter2 = read(pointer + 2, mode2)
    if (shouldJump(parameter1)) {
        pointer = parameter2
    } else {
        pointer += 3
    }
    run()
}

private fun compare(modes: List<ParameterMode>, compare: (Int, Int) -> Boolean) {
    val mode1 = modes.getOrElse(0) { ParameterMode.POSITION_MODE }
    val mode2 = modes.getOrElse(1) { ParameterMode.POSITION_MODE }
    val parameter1 = read(pointer + 1, mode1)
    val parameter2 = read(pointer + 2, mode2)
    if (compare(parameter1, parameter2)) {
        program[program[pointer + 3]] = 1
    } else {
        program[program[pointer + 3]] = 0
    }
    pointer += 4
    run()
}

private fun read(parameter: Int, mode: ParameterMode): Int {
    return when (mode) {
        ParameterMode.POSITION_MODE -> program[program[parameter]]
        ParameterMode.IMMEDIATE_MODE -> program[parameter]
    }
}

private fun parseOpCodeAndModes(instruction: Int): OpCodeAndModes {
    val instructions = instruction.toString().reversed().toCharArray()
    if (instructions.size == 1) return OpCodeAndModes(Character.getNumericValue(instructions[0]))
    val opCode = ("" + Character.getNumericValue(instructions[1]) + Character.getNumericValue(instructions[0])).toInt()
    val modes: List<ParameterMode> =
        instructions.slice(2 until instructions.size).map { ParameterMode.from(Character.getNumericValue(it)) }
            .toList()
    return OpCodeAndModes(opCode, modes)
}

private data class OpCodeAndModes(val opCode: Int, val modes: List<ParameterMode> = listOf())

private enum class ParameterMode {
    POSITION_MODE {
        override fun getCode(): Int = 0
    },
    IMMEDIATE_MODE {
        override fun getCode(): Int = 1
    };

    abstract fun getCode(): Int

    companion object {
        fun from(code: Int): ParameterMode {
            values().forEach { value -> if (code == value.getCode()) return value }
            throw IllegalArgumentException("Unrecognized code: $code")
        }
    }
}

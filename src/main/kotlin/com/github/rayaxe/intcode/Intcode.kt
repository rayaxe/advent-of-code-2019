package com.github.rayaxe.intcode

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KFunction2

class Intcode(val id: Int, program: List<Int>, val input: ReceiveChannel<Int>, val output: SendChannel<Int>) {

    private var memory: MutableList<Int> = program.toMutableList()
    private var pointer = 0
    var state = State.RUN
    var outputValue: Int? = null

    fun run() = runBlocking {
        while (state != State.HALT) {
            tick()
        }
        outputValue!!
    }

    private suspend fun tick() {
        val opCodeAndModes = parseOpCodeAndModes(memory[pointer])
        println("opCode=" + opCodeAndModes.opCode)
        when (val opCode = opCodeAndModes.opCode) {
            1 -> operation(opCodeAndModes.modes, Math::addExact)
            2 -> operation(opCodeAndModes.modes, Math::multiplyExact)
            3 -> input(input.receive())
            4 -> output(opCodeAndModes.modes)
            5 -> jump(opCodeAndModes.modes) { x -> x != 0 }
            6 -> jump(opCodeAndModes.modes) { x -> x == 0 }
            7 -> compare(opCodeAndModes.modes) { x, y -> x < y }
            8 -> compare(opCodeAndModes.modes) { x, y -> x == y }
            99 -> state = State.HALT
            else -> throw IllegalStateException("Unrecognized opcode: $opCode")
        }
    }

    private fun operation(modes: List<ParameterMode>, operation: KFunction2<Int, Int, Int>) {
        val mode1 = modes.getOrElse(0) { ParameterMode.POSITION_MODE }
        val mode2 = modes.getOrElse(1) { ParameterMode.POSITION_MODE }
        val parameter1 = read(pointer + 1, mode1)
        val parameter2 = read(pointer + 2, mode2)
        memory[memory[pointer + 3]] = operation(parameter1, parameter2)
        pointer += 4
    }

    private fun input(input: Int) {
        memory[memory[pointer + 1]] = input
        pointer += 2
    }

    private suspend fun output(modes: List<ParameterMode>) {
        val mode = modes.getOrElse(0) { ParameterMode.POSITION_MODE }
        val value = read(pointer + 1, mode)
        outputValue = value
        output.send(value)
        pointer += 2
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
    }

    private fun compare(modes: List<ParameterMode>, compare: (Int, Int) -> Boolean) {
        val mode1 = modes.getOrElse(0) { ParameterMode.POSITION_MODE }
        val mode2 = modes.getOrElse(1) { ParameterMode.POSITION_MODE }
        val parameter1 = read(pointer + 1, mode1)
        val parameter2 = read(pointer + 2, mode2)
        if (compare(parameter1, parameter2)) {
            memory[memory[pointer + 3]] = 1
        } else {
            memory[memory[pointer + 3]] = 0
        }
        pointer += 4
    }

    private fun read(parameter: Int, mode: ParameterMode): Int {
        return when (mode) {
            ParameterMode.POSITION_MODE -> memory[memory[parameter]]
            ParameterMode.IMMEDIATE_MODE -> memory[parameter]
        }
    }

    private fun parseOpCodeAndModes(instruction: Int): OpCodeAndModes {
        val instructions = instruction.toString().reversed().toCharArray()
        if (instructions.size == 1) return OpCodeAndModes(Character.getNumericValue(instructions[0]))
        val opCode =
            ("" + Character.getNumericValue(instructions[1]) + Character.getNumericValue(instructions[0])).toInt()
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
                values().forEach { if (code == it.getCode()) return it }
                throw IllegalArgumentException("Unrecognized code: $code")
            }
        }
    }

    enum class State {
        RUN, HALT
    }
}

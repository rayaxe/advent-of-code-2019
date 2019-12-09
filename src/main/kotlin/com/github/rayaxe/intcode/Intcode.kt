package com.github.rayaxe.intcode

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.runBlocking

class Intcode(
    val id: Long,
    program: List<Long>,
    private val input: ReceiveChannel<Long>,
    private val output: SendChannel<Long>
) {

    private var memory: MutableMap<Long, Long> = program
        .mapIndexed { index: Int, instruction: Long -> index.toLong() to instruction }
        .toMap().toMutableMap()
    private var pointer: Long = 0
    private var relativeBase: Long = 0
    var state = State.RUN
    var outputValue: Long? = null

    fun run() = runBlocking {
        while (state != State.HALT) {
            tick()
        }
        output.close()
        outputValue!!
    }

    private suspend fun tick() {
        val opCodeAndModes = parseOpCodeAndModes(memory[pointer]!!)
        when (val opCode = opCodeAndModes.opCode) {
            1 -> operation(opCodeAndModes.modes) { x, y -> x + y }
            2 -> operation(opCodeAndModes.modes) { x, y -> x * y }
            3 -> input(input.receive(), opCodeAndModes.modes)
            4 -> output(opCodeAndModes.modes)
            5 -> jump(opCodeAndModes.modes) { x -> x != 0L }
            6 -> jump(opCodeAndModes.modes) { x -> x == 0L }
            7 -> compare(opCodeAndModes.modes) { x, y -> x < y }
            8 -> compare(opCodeAndModes.modes) { x, y -> x == y }
            9 -> relativeBaseOffset(opCodeAndModes.modes)
            99 -> state = State.HALT
            else -> throw IllegalStateException("Unrecognized opcode: $opCode")
        }
    }

    private fun operation(modes: List<ParameterMode>, operation: (Long, Long) -> Long) {
        val mode1 = modes.getOrElse(0) { ParameterMode.POSITION_MODE }
        val mode2 = modes.getOrElse(1) { ParameterMode.POSITION_MODE }
        val mode3 = modes.getOrElse(2) { ParameterMode.POSITION_MODE }
        val parameter1 = read(pointer + 1, mode1)
        val parameter2 = read(pointer + 2, mode2)
        val parameter3 = read(pointer + 3, ParameterMode.IMMEDIATE_MODE)
        val offset = if (mode3 == ParameterMode.RELATIVE_MODE) relativeBase else 0L
        writeMemory(parameter3 + offset, operation(parameter1, parameter2))
        pointer += 4
    }

    private fun input(input: Long, modes: List<ParameterMode>) {
        val mode = modes.getOrElse(0) { ParameterMode.POSITION_MODE }
        val parameter = read(pointer + 1, ParameterMode.IMMEDIATE_MODE)
        val offset = if (mode == ParameterMode.RELATIVE_MODE) relativeBase else 0L
        writeMemory(parameter + offset, input)
        pointer += 2
    }

    private suspend fun output(modes: List<ParameterMode>) {
        val mode = modes.getOrElse(0) { ParameterMode.POSITION_MODE }
        val value = read(pointer + 1, mode)
        outputValue = value
        output.send(value)
        pointer += 2
    }

    private fun jump(modes: List<ParameterMode>, shouldJump: (Long) -> Boolean) {
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

    private fun compare(modes: List<ParameterMode>, compare: (Long, Long) -> Boolean) {
        val mode1 = modes.getOrElse(0) { ParameterMode.POSITION_MODE }
        val mode2 = modes.getOrElse(1) { ParameterMode.POSITION_MODE }
        val mode3 = modes.getOrElse(2) { ParameterMode.POSITION_MODE }
        val parameter1 = read(pointer + 1, mode1)
        val parameter2 = read(pointer + 2, mode2)
        val parameter3 = read(pointer + 3, ParameterMode.IMMEDIATE_MODE)
        val offset = if (mode3 == ParameterMode.RELATIVE_MODE) relativeBase else 0L
        val result: Long = if (compare(parameter1, parameter2)) 1L else 0L
        writeMemory(parameter3 + offset, result)
        pointer += 4
    }

    private fun relativeBaseOffset(modes: List<ParameterMode>) {
        val mode = modes.getOrElse(0) { ParameterMode.POSITION_MODE }
        relativeBase += read(pointer + 1, mode).toInt()
        pointer += 2
    }

    private fun read(parameter: Long, mode: ParameterMode): Long {
        return when (mode) {
            ParameterMode.POSITION_MODE -> readMemory(readMemory(parameter))
            ParameterMode.IMMEDIATE_MODE -> readMemory(parameter)
            ParameterMode.RELATIVE_MODE -> readMemory(readMemory(parameter) + relativeBase)
        }
    }

    private fun readMemory(index: Long): Long {
        return memory.getOrPut(index, { 0L })
    }

    private fun writeMemory(index: Long, value: Long) {
        memory[index] = value
    }

    private fun parseOpCodeAndModes(instruction: Long): OpCodeAndModes {
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
            override fun getCode() = 0
        },
        IMMEDIATE_MODE {
            override fun getCode() = 1
        },
        RELATIVE_MODE {
            override fun getCode() = 2
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

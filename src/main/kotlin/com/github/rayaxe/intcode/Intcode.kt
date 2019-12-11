package com.github.rayaxe.intcode

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.runBlocking

class Intcode(program: List<Long>, private val input: ReceiveChannel<Long>, private val output: SendChannel<Long>) {

    private var memory = program
        .mapIndexed { index: Int, instruction: Long -> index.toLong() to instruction }
        .toMap().toMutableMap()
    private var pointer = 0L
    private var relativeBase = 0L
    var state = State.RUN

    fun run() = runBlocking {
        while (state != State.HALT) {
            step()
        }
    }

    private suspend fun step() {
        val opcodeAndParameterModes = parse(memory[pointer]!!)
        val parameterModes = opcodeAndParameterModes.parameterModes
        when (val opcode = opcodeAndParameterModes.opcode) {
            1 -> calculate(parameterModes) { x, y -> x + y }
            2 -> calculate(parameterModes) { x, y -> x * y }
            3 -> input(parameterModes)
            4 -> output(parameterModes)
            5 -> jump(parameterModes) { x -> x != 0L }
            6 -> jump(parameterModes) { x -> x == 0L }
            7 -> compare(parameterModes) { x, y -> x < y }
            8 -> compare(parameterModes) { x, y -> x == y }
            9 -> relativeBaseOffset(parameterModes)
            99 -> {
                state = State.HALT; output.close()
            }
            else -> throw IllegalStateException("Unrecognized opcode: $opcode")
        }
    }

    private fun calculate(parameterModes: List<ParameterMode>, operation: (Long, Long) -> Long) {
        val parameter1 = read(pointer + 1, parameterModes[0])
        val parameter2 = read(pointer + 2, parameterModes[1])
        write(pointer + 3, parameterModes[2], operation(parameter1, parameter2))
        pointer += 4
    }

    private suspend fun input(parameterModes: List<ParameterMode>) {
        val value = input.receive()
        write(pointer + 1, parameterModes[0], value)
//        println("Intcode receives: $value")
        pointer += 2
    }

    private suspend fun output(parameterModes: List<ParameterMode>) {
        val value = read(pointer + 1, parameterModes[0])
//        println("Intcode sends: $value")
        output.send(value)
        pointer += 2
    }

    private fun jump(parameterModes: List<ParameterMode>, shouldJump: (Long) -> Boolean) {
        val parameter1 = read(pointer + 1, parameterModes[0])
        val parameter2 = read(pointer + 2, parameterModes[1])
        if (shouldJump(parameter1)) {
            pointer = parameter2
        } else {
            pointer += 3
        }
    }

    private fun compare(parameterModes: List<ParameterMode>, compare: (Long, Long) -> Boolean) {
        val parameter1 = read(pointer + 1, parameterModes[0])
        val parameter2 = read(pointer + 2, parameterModes[1])
        val value = if (compare(parameter1, parameter2)) 1L else 0L
        write(pointer + 3, parameterModes[2], value)
        pointer += 4
    }

    private fun relativeBaseOffset(parameterModes: List<ParameterMode>) {
        relativeBase += read(pointer + 1, parameterModes[0])
        pointer += 2
    }

    private fun read(parameter: Long, parameterMode: ParameterMode): Long {
        return when (parameterMode) {
            ParameterMode.POSITION_MODE -> readSafe(readSafe(parameter))
            ParameterMode.IMMEDIATE_MODE -> readSafe(parameter)
            ParameterMode.RELATIVE_MODE -> readSafe(readSafe(parameter) + relativeBase)
        }
    }

    private fun readSafe(index: Long): Long {
        return memory.getOrPut(index, { 0L })
    }

    private fun write(address: Long, parameterMode: ParameterMode, value: Long) {
        val parameterValue = read(address, ParameterMode.IMMEDIATE_MODE)
        val offset = if (parameterMode == ParameterMode.RELATIVE_MODE) relativeBase else 0L
        memory[parameterValue + offset] = value
    }

    private fun parse(opcodeAndParameterModes: Long): OpcodeAndParameterModes {
        val opcode = (opcodeAndParameterModes % 100).toInt()
        val parameterModeCodes = opcodeAndParameterModes.toString().reversed().toCharArray()
        val parameterModes = (2..4)
            .map { Character.getNumericValue(parameterModeCodes.getOrElse(it) { '0' }) }
            .map { ParameterMode.from(it) }.toList()
        return OpcodeAndParameterModes(opcode, parameterModes)
    }

    private data class OpcodeAndParameterModes(val opcode: Int, val parameterModes: List<ParameterMode> = listOf())

    private enum class ParameterMode(val code: Int) {
        POSITION_MODE(0),
        IMMEDIATE_MODE(1),
        RELATIVE_MODE(2);

        companion object {
            fun from(code: Int) =
                values().find { it.code == code } ?: throw IllegalArgumentException("Unrecognized code: $code")
        }
    }

    enum class State {
        RUN, HALT
    }
}

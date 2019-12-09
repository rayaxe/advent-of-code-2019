package com.github.rayaxe.days

import com.github.rayaxe.intcode.Intcode
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun day9(program: List<Long>, systemId: Long, setPhase: Boolean = true): List<Long> = runBlocking {
    val input = Channel<Long>()
    val output = Channel<Long>()
    val intcode = Intcode(systemId, program, input, output)
    launch { intcode.run() }
    if (setPhase) {
        input.send(systemId)
    }
    val result = mutableListOf<Long>()
    for (value in output) {
        result.add(value)
    }
    result
}

package com.github.rayaxe.days

import com.github.rayaxe.intcode.Intcode
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking

fun day5(program: List<Int>, systemId: Int): Int = runBlocking {
    val input = Channel<Int>()
    val output = Channel<Int>()
    val intcode = Intcode(systemId, program, input, output)
    val deferred = async { intcode.run() }
    input.send(systemId)
    output.receive()
    deferred.await()
}

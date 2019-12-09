package com.github.rayaxe.days

import com.github.rayaxe.intcode.Intcode
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking

fun day5(program: List<Long>, systemId: Long): Long = runBlocking {
    val input = Channel<Long>()
    val output = Channel<Long>()
    val intcode = Intcode(systemId, program, input, output)
    val deferred = async { intcode.run() }
    input.send(systemId)
    output.receive()
    deferred.await()
}

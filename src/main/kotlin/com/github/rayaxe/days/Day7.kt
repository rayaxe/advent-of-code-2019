package com.github.rayaxe.days

import com.github.rayaxe.intcode.Intcode
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun day7Part1(program: List<Long>): Long {
    val phaseSequences: List<List<Int>> = permute((0..4).toList())
    return phaseSequences.map { day7(program, it) }.max()!!
}

fun day7Part2(program: List<Long>): Long {
    val phaseSequences: List<List<Int>> = permute((5..9).toList())
    return phaseSequences.map { day7(program, it) }.max()!!
}

fun day7(program: List<Long>, phaseSequence: List<Int>): Long = runBlocking {
    val channels: List<Channel<Long>> = listOf(Channel(), Channel(), Channel(), Channel(), Channel())
    val amplifiers: List<Pair<Intcode, Int>> = (0..4)
        .map {
            val source = (it + 4) % 5
            val target = it
            val intcode = Intcode(it.toLong(), program, channels[source], channels[target])
            Pair(intcode, it)
        }
        .toList()
    println("=== Run amplifiers ===")
    launch { amplifiers[0].first.run() }
    launch { amplifiers[1].first.run() }
    launch { amplifiers[2].first.run() }
    launch { amplifiers[3].first.run() }
    val deferred = async { amplifiers[4].first.run() }
    println("=== Send phase setting ===")
    channels[0].send(phaseSequence[amplifiers[0].second].toLong())
    channels[1].send(phaseSequence[amplifiers[1].second].toLong())
    channels[2].send(phaseSequence[amplifiers[2].second].toLong())
    channels[3].send(phaseSequence[amplifiers[3].second].toLong())
    channels[4].send(phaseSequence[amplifiers[4].second].toLong())
    println("=== Send start signal ===")
    channels[0].send(0)
    println("=== Running... ===")
    deferred.await()
    coroutineContext.cancelChildren()
    println("=== Read output ===")
    channels[0].receive()
}

fun <T> permute(list: List<T>): List<List<T>> {
    if (list.size == 1) return listOf(list)
    val perms = mutableListOf<List<T>>()
    val sub = list[0]
    for (perm in permute(list.drop(1)))
        for (i in 0..perm.size) {
            val newPerm = perm.toMutableList()
            newPerm.add(i, sub)
            perms.add(newPerm)
        }
    return perms
}

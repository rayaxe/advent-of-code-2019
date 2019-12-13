package com.github.rayaxe.days

import com.github.rayaxe.intcode.Intcode
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.receiveOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private enum class Tile {
    EMPTY, WALL, BLOCK, PADDLE, BALL
}

fun day13Part1(program: List<Long>): Int = runBlocking {
    val input = Channel<Long>()
    val output = Channel<Long>()
    val intcode = Intcode(program, input, output)

    launch { intcode.run() }

    val tiles = mutableMapOf<Pair<Int, Int>, Tile>()
    while (true) {
        val x = output.receiveOrNull()?.toInt() ?: break
        val y = output.receive().toInt()
        val tile = Tile.values()[output.receive().toInt()]
        tiles[Pair(x, y)] = tile
    }

    tiles.filter { it.value == Tile.BLOCK }.count()
}

fun day13Part2(program: List<Long>): Int = runBlocking {
    val game = program.toMutableList()
    // Play for free
    game[0] = 2L

    val input = Channel<Long>()
    val output = Channel<Long>()
    val intcode = Intcode(game, input, output)
    val joystick = Joystick(input, output)

    val deferred = async { intcode.run() }
    launch { joystick.run() }
    input.send(0L)
    deferred.await()

    joystick.score
}

private class Joystick(val input: Channel<Long>, val output: Channel<Long>) {
    var score = 0
    suspend fun run() {
        var paddleX = 0
        var joyStickX: Int
        var init = true
        while (true) {
            val x = output.receiveOrNull()?.toInt() ?: break
            val y = output.receive().toInt()
            val value = output.receive().toInt()
            if (x == -1 && y == 0) {
                score = value
            } else {
                val tile = Tile.values()[value]
                if (tile == Tile.BALL) {
                    joyStickX = when {
                        paddleX > x -> -1
                        paddleX < x -> 1
                        else -> 0
                    }
                    if (!init) {
                        input.send(joyStickX.toLong())
                    }
                    init = false
                } else if (tile == Tile.PADDLE) {
                    paddleX = x
                }
            }
        }
    }
}

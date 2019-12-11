package com.github.rayaxe.days

import com.github.rayaxe.intcode.Intcode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun day11(program: List<Long>, firstPanelColor: Int): Int = runBlocking {
    val input = Channel<Long>()
    val output = Channel<Long>()
    val intcode = Intcode(program, input, output)
    val robot = Robot(output, input, firstPanelColor)

    val deferred = async { intcode.run() }
    launch { robot.run() }
    deferred.await()

    draw(robot)

    robot.paintedPanels.count()
}

private fun draw(robot: Robot) {
    val minY = robot.paintedPanels.minBy { it.y }!!.y
    val maxY = robot.paintedPanels.maxBy { it.y }!!.y
    val minX = robot.paintedPanels.minBy { it.x }!!.x
    val maxX = robot.paintedPanels.maxBy { it.x }!!.x
    for (y in minY..maxY) {
        for (x in minX..maxX) {
            when (val color = robot.hull.getOrDefault(Robot.Panel(x, y), Robot.Panel(x, y)).color) {
                0 -> print(".")
                1 -> print("#")
                else -> throw IllegalArgumentException("Unrecognized color: $color")
            }
        }
        println()
    }
}

private class Robot(val input: ReceiveChannel<Long>, val output: SendChannel<Long>, val firstColor: Int) {
    var x = 0
    var y = 0
    var state = State.PAINT
    var direction = Direction.UP

    var isFirstPanel = true

    val hull = mutableMapOf<Panel, Panel>()
    val paintedPanels = mutableSetOf<Panel>()

    suspend fun run() {
        while (!input.isClosedForReceive) {
            state = when (state) {
                State.PAINT -> {
                    paint()
                    State.MOVE
                }
                State.MOVE -> {
                    move()
                    State.PAINT
                }
            }
        }
    }

    private suspend fun paint() {
        val panel = getPanel()
        if (isFirstPanel) {
            output.send(firstColor.toLong())
            isFirstPanel = false
        } else {
            output.send(panel.color.toLong())
        }
        val colorInput = input.receive().toInt()
//        println("Robot receives color: $colorInput")
        when (colorInput) {
            0 -> panel.color = colorInput
            1 -> panel.color = colorInput
            else -> throw IllegalArgumentException("Unrecognized color input: $colorInput")
        }
        paintedPanels.add(panel)
    }

    private suspend fun move() {
        val directionInput = input.receive().toInt()
//        println("Robot receives direction: $directionInput")
        direction = when (directionInput) {
            0 -> Direction.turnLeft(direction)
            1 -> Direction.turnRight(direction)
            else -> throw IllegalArgumentException("Unrecognized direction input: $directionInput")
        }
        when (direction) {
            Direction.UP -> y -= 1
            Direction.RIGHT -> x += 1
            Direction.DOWN -> y += 1
            Direction.LEFT -> x -= 1
        }
    }

    private fun getPanel() = hull.getOrPut(Panel(x, y)) { Panel(x, y) }

    internal data class Panel(val x: Int, val y: Int) {
        var color: Int = 0
    }

    enum class State {
        PAINT, MOVE
    }

    enum class Direction {
        UP, RIGHT, DOWN, LEFT;

        companion object {
            fun turnLeft(direction: Direction): Direction {
                return when (direction) {
                    UP -> LEFT
                    RIGHT -> UP
                    DOWN -> RIGHT
                    LEFT -> DOWN
                }
            }

            fun turnRight(direction: Direction): Direction {
                return when (direction) {
                    UP -> RIGHT
                    RIGHT -> DOWN
                    DOWN -> LEFT
                    LEFT -> UP
                }
            }
        }
    }
}

package com.github.rayaxe.days

fun day8Part1(encodedImage: String, width: Int = 25, height: Int = 6): Int {
    val layer = encodedImage.chunked(width * height)
        .map { Pair(it.count('0'), it) }
        .minBy { it.first }!!
        .second
    return layer.count('1') * layer.count('2')
}

private fun String.count(char: Char) = this.filter { it == char }.length

fun day8Part2(encodedImage: String, width: Int = 25, height: Int = 6): String {
    val pixels = width * height
    val layers = encodedImage.chunked(pixels).map { it.toCharArray() }
    val output = "2".repeat(pixels).toCharArray()
    layers.forEach {
        for (i in 0 until pixels) {
            if (output[i] == '2') {
                output[i] = it[i]
            }
        }
    }
    val decodedImage = output.joinToString("")
    printImage(decodedImage, width)
    return decodedImage
}

private fun printImage(decodedImage: String, width: Int) {
    decodedImage
        .chunked(width)
        .map {
            it
                .replace('0', ' ')
                .replace('1', '@')
        }
        .forEach { println(it) }
}

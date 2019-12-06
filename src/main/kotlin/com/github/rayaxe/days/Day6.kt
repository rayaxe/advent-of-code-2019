package com.github.rayaxe.days

private var objects: MutableMap<String, Object> = mutableMapOf()

fun day6Part1(orbits: List<String>): Int {
    orbits.forEach { parse(it) }
    val root: Object = objects.values.first { it.parent == null }
    return count(root, 0)
}

fun day6Part2(orbits: List<String>): Int {
    orbits.stream().forEach { parse(it) }
    return -1
}

private fun count(obj: Object, depth: Int): Int {
    return depth + obj.children.map { count(it, depth + 1) }.toList().sum()
}

private fun parse(orbit: String) {
    val parts = orbit.split(')')
    val parent = objects.getOrPut(parts[0]) { Object(parts[0]) }
    val child = objects.getOrPut(parts[1]) { Object(parts[1]) }
    child.parent = parent
    parent.children.add(child)
}

private data class Object(val name: String) {
    var parent: Object? = null
    var children: MutableSet<Object> = mutableSetOf()
}

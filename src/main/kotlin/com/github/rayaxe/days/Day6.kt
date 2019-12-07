package com.github.rayaxe.days

private var objects: MutableMap<String, Object> = mutableMapOf()
private var visited: MutableSet<Object> = mutableSetOf()
private var santa = 0

fun day6Part1(orbits: List<String>): Int {
    orbits.forEach { parse(it) }
    val root: Object = objects.values.first { it.parent == null }
    return count(root, 0)
}

fun day6Part2(orbits: List<String>): Int {
    orbits.forEach { parse(it) }
    val you: Object = objects.get("YOU")!!
    traverse(you, 0)
    return santa
}

private fun count(obj: Object, depth: Int): Int {
    return depth + obj.children.map { count(it, depth + 1) }.sum()
}

private fun traverse(obj: Object, depth: Int) {
    if (obj.name == "SAN") {
        santa = depth - 2
    }

    listOfNotNull(obj.parent).union(obj.children)
        .filter { !visited.contains(it) }
        .forEach {
            visited.add(it)
            traverse(it, depth + 1)
        }
}

private fun parse(orbit: String) {
    val parts = orbit.split(')')
    val parentName = parts[0]
    val childName = parts[1]
    val parent = objects.getOrPut(parentName) { Object(parentName) }
    val child = objects.getOrPut(childName) { Object(childName) }
    child.parent = parent
    parent.children.add(child)
}

private data class Object(val name: String) {
    var parent: Object? = null
    var children: MutableSet<Object> = mutableSetOf()
}

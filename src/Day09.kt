import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val sim = Simulation(2)
        input.forEach { sim.move(it) }
        return sim.tailHistory.size
    }

    fun part2(input: List<String>): Int {
        val sim = Simulation(10)
        input.forEach { sim.move(it) }
        return sim.tailHistory.size
    }

    // test if implementation meets criteria from the description, like:
    val day = "09"
    val testInput = readLines("Day${day}_test")
    check(part1(testInput) == 13)
//    check(part2(testInput) == 36)

    val input = readLines("Day$day")
    println(part1(input))
    println(part2(input))
}

data class Move(val direction: String, val times: Int)
class Simulation(private val length: Int) {
    var rope = Array(length) { Point(0, 0) }
    val tailHistory = mutableSetOf<Point>()

    fun move(line: String) {
        val move = parseMove(line)

        repeat(move.times) {
            moveHead(move.direction)

            for (s in 1 until length) {
                moveTail(s)
                tailHistory.add(rope.last())
            }
        }
    }

    private fun parseMove(instruction: String): Move {
        val (direction, times) = instruction.split(" ")
        return Move(direction, times.toInt())
    }

    fun moveHead(direction: String) {
        val head = rope.first()

        rope[0] = when (direction) {
            "U" -> Point(head.x, head.y + 1)
            "D" -> Point(head.x, head.y - 1)
            "L" -> Point(head.x + 1, head.y)
            "R" -> Point(head.x - 1, head.y)
            else -> error("Invalid direction")
        }
    }

    fun isAdjacent(head: Point, tail: Point) = abs(head.x - tail.x) <= 1 && abs(head.y - tail.y) <= 1

    fun moveTail(segment: Int) {
        val head = rope[segment - 1]
        val tail = rope[segment]

        if (isAdjacent(head, tail)) return

        val dx = getDelta(tail.x, head.x)
        val dy = getDelta(tail.y, head.y)
        rope[segment] = Point(tail.x + dx, tail.y + dy)
    }

    private fun getDelta(a: Int, b: Int) = when {
        a < b -> 1
        a > b -> -1
        else -> 0
    }
}
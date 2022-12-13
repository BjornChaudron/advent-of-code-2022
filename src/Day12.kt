fun main() {
    fun part1(input: List<String>): Int {
        val grid = input.flatMapIndexed { y, line -> line.mapIndexed { x, c -> Point(x, y) to c } }.toMap()
        return findShortestPath(grid, goal = 'S')
    }

    fun part2(input: List<String>): Int {
        val grid = input.flatMapIndexed { y, line -> line.mapIndexed { x, c -> Point(x, y) to c } }.toMap()
        return findShortestPath(grid, goal = 'a')
    }

    // test if implementation meets criteria from the description, like:
    val day = "12"
    val testInput = readLines("Day${day}_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readLines("Day$day")
    println(part1(input))
    println(part2(input))
}

// Breadth-first search
fun findShortestPath(grid: Grid<Char>, goal: Char): Int {
    val start = grid.entries.first { it.value == 'E' }.key
    val distances = grid.keys.associateWith { Int.MAX_VALUE }.toMutableMap()
    distances[start] = 0

    val q = ArrayDeque<Point>()
    q.add(start)

    while (q.isNotEmpty()) {
        val current = q.removeFirst()
        current.validNeighbours(grid)
            .forEach { neighbour ->
                val newDistance = distances[current]!! + 1

                if (grid[neighbour] == goal) return newDistance

                if (newDistance < distances[neighbour]!!) {
                    distances[neighbour] = newDistance
                    q.addLast(neighbour)
                }
            }
    }
    error("No path found")
}

private fun Point.validNeighbours(grid: Grid<Char>) =
    neighbours.filter { it in grid && grid[it]!!.elevation - grid[this]!!.elevation >= -1
}

private val Char.elevation: Int
    get() = when (this) {
        'S' -> 'a'.code
        'E' -> 'z'.code
        else -> this.code
    }
val priorities = ('a'..'z').mapPriorities(1) + ('A'..'Z').mapPriorities(27)

fun CharRange.mapPriorities(target: Int): Map<Char, Int> {
    val delta = target - first.code
    return associateWith { it.code + delta }
}

fun groupSacks(sacks: List<String>): List<Triple<Set<Char>, Set<Char>, Set<Char>>> {
    val groupsSize = 3
    val groups = mutableListOf<Triple<Set<Char>, Set<Char>, Set<Char>>>()
    for (i in sacks.indices step groupsSize) {
        val triple = Triple(sacks[i].toSet(), sacks[i + 1].toSet(), sacks[i + 2].toSet())
        groups.add(triple)
    }
    return groups
}

fun main() {
    fun part1(sacks: List<String>) = sacks.asSequence()
            .map { sack ->
                val nrHalfContents = sack.length / 2
                sack.take(nrHalfContents).toSet() to sack.takeLast(nrHalfContents).toSet()
            }
            .map { sack -> sack.first.intersect(sack.second) }
            .flatMap { it.toList() }
            .sumOf { priorities[it]!! }

    fun part2(sacks: List<String>) =
        groupSacks(sacks)
            .map { it.first.intersect(it.second).intersect(it.third) }
            .flatMap { it.toList() }
            .sumOf { priorities[it]!! }

    // test if implementation meets criteria from the description, like:
    val day = "03"
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}
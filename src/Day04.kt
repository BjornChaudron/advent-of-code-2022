fun String.toIntRanges(): Pair<IntRange, IntRange> {
    fun String.toIntRange(): IntRange {
        val (start, end) = split("-").map { it.toInt() }
        return start..end
    }
    val (first, second) = split(",")
    return Pair(first.toIntRange(), second.toIntRange())
}

fun IntRange.contains(other: IntRange) = start <= other.first && endInclusive >= other.last
fun IntRange.overlaps(other: IntRange) = intersect(other).isNotEmpty()

fun main() {
    fun part1(pairLines: List<String>) =
        pairLines.map { it.toIntRanges() }
            .count { pair -> pair.first.contains(pair.second) || pair.second.contains(pair.first) }

    fun part2(pairLines: List<String>) =
        pairLines.map { it.toIntRanges() }
            .count { pair -> pair.first.overlaps(pair.second) || pair.second.overlaps(pair.first) }

    // test if implementation meets criteria from the description, like:
    val day = "04"
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}

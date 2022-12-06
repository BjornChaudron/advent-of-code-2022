fun main() {
    fun part1(input: String) = findMarker(input, 4)

    fun part2(input: String) = findMarker(input, 14)

    // test if implementation meets criteria from the description, like:
    val day = "06"
    val testInput = read("Day${day}_test")
    check(part1(testInput) == 7)

    val input = read("Day$day")
    println(part1(input))
    println(part2(input))
}

fun findMarker(data: String, seqLength: Int): Int {
    for (i in seqLength..data.lastIndex) {
        val seq = data.subSequence(i - seqLength, i).toSet()
        if (seq.size == seqLength) {
            return i
        }
    }
    return -1
}

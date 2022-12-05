fun main() {
    fun part1(input: List<String>): Int {
        return sortByTotalCalories(input).first()
    }

    fun part2(input: List<String>): Int {
        return sortByTotalCalories(input).subList(0, 3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

fun sortByTotalCalories(input: List<String>): List<Int> {
    val totalCalories = mutableListOf<Int>()

    var currentCalories = 0
    for (index in input.indices) {
        val line = input[index]
        if (line.isEmpty()) {
            totalCalories.add(currentCalories)
            currentCalories = 0
            continue
        }

        currentCalories += line.toInt()
        if (index == input.lastIndex) {
            totalCalories.add(currentCalories)
        }
    }

    totalCalories.sortDescending()
    return totalCalories.toList()
}

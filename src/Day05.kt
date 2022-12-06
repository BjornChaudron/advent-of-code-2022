fun main() {
    fun part1(input: List<String>): String {
        val (stacks, commands) = parseInput(input)
        val crane = Crane(stacks, moveMultiple = false)
        crane.executeCommands(commands)

        return joinTopCrates(stacks)
    }

    fun part2(input: List<String>): String {
        val (stacks, commands) = parseInput(input)
        val crane = Crane(stacks, moveMultiple = true)
        crane.executeCommands(commands)

        return joinTopCrates(stacks)
    }

    // test if implementation meets criteria from the description, like:
    val day = "05"
    val testInput = readLines("Day${day}_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readLines("Day$day")
    println(part1(input))
    println(part2(input))
}

fun parseInput(input: List<String>) = Pair(parseStacks(input), parseCommands(input))

fun parseStacks(lines: List<String>): Map<Int, ArrayDeque<Char>> {
    val image = lines.takeWhile { line -> line.isNotBlank() }

    val numbersLine = image.last()
    val stackLookup = numbersLine.mapIndexed { i, c -> i to c.digitToIntOrNull() }
        .associate { pair -> pair }
        .filterValues { it != null }

    val stacks = (1..stackLookup.size).associateWith { ArrayDeque<Char>() }

    image.dropLast(1)
        .forEach { line ->
            stackLookup.filterKeys { it < line.length }
                .forEach { (index, stackNr) ->
                    val char = line[index]
                    if (!char.isWhitespace()) stacks[stackNr]?.addFirst(char)
                }
        }
    return stacks.toMap()
}

fun parseCommands(input: List<String>): List<Command> =
    input.takeLastWhile { it.isNotBlank() }
        .map { line ->
            val (times, from, to) = line.split(" ").mapNotNull { it.toIntOrNull() }
            Command(times, from, to)
        }

fun joinTopCrates(stacks: Map<Int, ArrayDeque<Char>>): String = stacks.entries
    .sortedBy { (k, _) -> k }
    .map { (_, v) -> v.last() }
    .joinToString("")

data class Command(val crates: Int, val from: Int, val to: Int)

class Crane(private val stacks: Map<Int, ArrayDeque<Char>>, private val moveMultiple: Boolean) {

    fun executeCommands(commands: List<Command>) = commands.forEach(::executeCommand)
    private fun executeCommand(command: Command) = if (moveMultiple) moveStack(command) else moveCrate(command)

    private fun moveCrate(command: Command) = with(command) {
        val size = stacks[from]?.size ?: 0
        val times = minOf(size, crates)

        repeat(times) {
            val crate = stacks[from]?.removeLast()
            if (crate != null) stacks[to]?.addLast(crate)
        }
    }

    private fun moveStack(command: Command)= with(command) {
        val stackSize = stacks[from]?.size ?: 0
        val size = minOf(stackSize, crates)

        val crates = stacks[from]?.takeLast(size) ?: emptyList()
        repeat(crates.size) { stacks[from]?.removeLast() }
        stacks[to]?.addAll(crates)
    }
}
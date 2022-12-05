fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.uppercase().split(" ") }
            .map {
                val opponent = parseChoice(it[0])
                val player = parseChoice(it[1])

                getScore(player) + getMatchBonus(player, opponent)
            }.sum()
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.uppercase().split(" ") }
            .map {
                val opponent = parseChoice(it[0])
                val result = parseMatchResult(it[1])
                val player = getFixedChoice(opponent, result)

                getScore(player) + getMatchBonus(player, opponent)
            }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val day = "02"
    val testInput = readInput("Day${day}_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}

fun parseChoice(choice: String) = when (choice) {
    "X", "A" -> "rock"
    "Y", "B" -> "paper"
    "Z", "C" -> "scissors"
    else -> error("Invalid choice!")
}

fun getScore(choice: String) = when (choice) {
    "rock" -> 1
    "paper" -> 2
    "scissors" -> 3
    else -> error("Invalid choice!")
}

fun getMatchBonus(playerChoice: String, opponentChoice: String) =
    when {
        playerChoice == "rock" && opponentChoice == "scissors" -> 6
        playerChoice == "paper" && opponentChoice == "rock" -> 6
        playerChoice == "scissors" && opponentChoice == "paper" -> 6
        playerChoice == opponentChoice -> 3
        else -> 0
    }

fun parseMatchResult(result: String) =
    when (result) {
        "X" -> "loss"
        "Y" -> "draw"
        "Z" -> "win"
        else -> error("Invalid choice!")
    }

fun getFixedChoice(opponentChoice: String, result: String) =
    when {
        opponentChoice == "rock" && result == "win" -> "paper"
        opponentChoice == "rock" && result == "loss" -> "scissors"
        opponentChoice == "paper" && result == "win" -> "scissors"
        opponentChoice == "paper" && result == "loss" -> "rock"
        opponentChoice == "scissors" && result == "win" -> "rock"
        opponentChoice == "scissors" && result == "loss" -> "paper"
        else -> opponentChoice
    }
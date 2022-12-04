fun main() {
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

/**
 * Determines the score that the second player would achieve following the given strategy guide.
 */
private fun part1(input: List<String>): Int {
    return input.map { GameResult(it) }
        .sumOf { it.player2Score() }
}

private fun part2(input: List<String>): Int {
    return input.map { GameResult2(it) }
        .sumOf { it.player2Score() }
}

/**
 * For part 1.
 */
private class GameResult(input: String) {
    enum class Shape(val identifiers: List<String>, val score: Int) {
        ROCK(listOf("A", "X"), 1),
        PAPER(listOf("B", "Y"), 2),
        SCISSORS(listOf("C", "Z"), 3)
    }

    enum class Outcome(val score: Int) {
        LOSS(0),
        DRAW(3),
        WIN(6)
    }

    val player1Shape: Shape
    val player2Shape: Shape

    init {
        val parsedInput = input.split(" ")
        player1Shape = parsedInput.first().toShape()
        player2Shape = parsedInput.last().toShape()
    }

    fun String.toShape() = Shape.values().first { shape -> this in shape.identifiers }

    fun player2Score(): Int {
        return player2Shape.score + getOutcome(player2Shape, player1Shape).score
    }

    /**
     * Determines the outcome of the game from the perspective of the first player.
     */
    fun getOutcome(firstPlayer: Shape, secondPlayer: Shape): Outcome {
        return when {
            firstPlayer == secondPlayer -> Outcome.DRAW
            secondPlayer == BEATS[firstPlayer] -> Outcome.WIN
            else -> Outcome.LOSS
        }
    }

    companion object {
        val BEATS = mapOf(
            Shape.ROCK to Shape.SCISSORS,
            Shape.PAPER to Shape.ROCK,
            Shape.SCISSORS to Shape.PAPER
        )
    }
}

/**
 * For part 2.
 */
private class GameResult2(input: String) {
    enum class Shape(val identifier: String, val score: Int) {
        ROCK("A", 1),
        PAPER("B", 2),
        SCISSORS("C", 3)
    }

    enum class Outcome(val identifier: String, val score: Int) {
        LOSS("X", 0),
        DRAW("Y", 3),
        WIN("Z", 6)
    }

    val requiredOutcome: Outcome
    val player2Shape: Shape

    init {
        val parsedInput = input.split(" ")
        val player1Shape = parsedInput.first().toShape()
        requiredOutcome = parsedInput.last().toOutcome()

        player2Shape = when (requiredOutcome) {
            Outcome.LOSS -> BEATS[player1Shape]!!
            Outcome.DRAW -> player1Shape
            Outcome.WIN -> BEATS.entries.first { (_, losingShape) -> player1Shape == losingShape }.key
        }
    }

    fun String.toShape() = Shape.values().first { shape -> this == shape.identifier }

    fun String.toOutcome() = Outcome.values().first { outcome -> this == outcome.identifier }

    fun player2Score(): Int {
        return player2Shape.score + requiredOutcome.score
    }

    companion object {
        val BEATS = mapOf(
            Shape.ROCK to Shape.SCISSORS,
            Shape.PAPER to Shape.ROCK,
            Shape.SCISSORS to Shape.PAPER
        )
    }
}

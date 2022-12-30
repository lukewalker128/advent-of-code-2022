fun main() {
    val testInput = readInput("Day06_test")
    checkEquals(7, part1(testInput))
    checkEquals(19, part2(testInput))

    val input = readInput("Day06")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

/**
 * Returns the position of the first start-of-packet marker in the given datastream.
 */
private fun part1(input: List<String>): Int {
    return findUniqueWindow(input.first(), 4) + 1
}

/**
 * Returns the ending position of the first window in the input string that comprises completely unique characters.
 */
private fun findUniqueWindow(input: String, windowSize: Int): Int {
    require(input.length >= windowSize)

    val charCounts = mutableMapOf<Char, Int>()

    fun add(char: Char) {
        charCounts[char] = charCounts.getOrDefault(char, 0) + 1
    }

    fun remove(char: Char) {
        if (charCounts[char] == 1) {
            charCounts.remove(char)
        } else {
            charCounts[char] = charCounts[char]!! - 1
        }
    }

    // populate counts for the first windowSize - 1 characters
    input.substring(0 until windowSize - 1).forEach { add(it) }

    for (i in windowSize - 1..input.lastIndex) {
        if (i - windowSize >= 0) {
            remove(input[i - windowSize])
        }

        add(input[i])

        if (charCounts.size == windowSize) {
            // all characters in the window are unique
            return i
        }
    }

    return -1
}

/**
 * Returns the position of the first start-of-message marker in the given datastream.
 */
private fun part2(input: List<String>): Int {
    return findUniqueWindow(input.first(), 14) + 1
}

fun main() {
    val testInput = readInput("Day04_test")
    val testAssignmentPairs = testInput.toAssignmentPairs()
    checkEquals(2, part1(testAssignmentPairs))
    checkEquals(4, part2(testAssignmentPairs))

    val input = readInput("Day04")
    val assignmentPairs = input.toAssignmentPairs()
    println("Part 1: ${part1(assignmentPairs)}")
    println("Part 2: ${part2(assignmentPairs)}")
}

/**
 * Counts the number of assignment pairs for which one is contained entirely within the other.
 */
private fun part1(assignmentPairs: List<Pair<IntRange, IntRange>>): Int {
    return assignmentPairs.count { (first, second) -> first.within(second) || second.within(first) }
}

private fun List<String>.toAssignmentPairs() = map { line ->
    line.split(',').let {
        require(it.size == 2)
        Pair(it.first().toIntRange(), it.last().toIntRange())
    }
}

private fun String.toIntRange() = split('-').let {
    require(it.size == 2)
    val first = it.first().toInt()
    val second = it.last().toInt()

    require(first <= second)
    IntRange(first, second)
}

private fun IntRange.within(other: IntRange) = first >= other.first && last <= other.last

/**
 * Counts the number of assignment pairs that overlap at all.
 */
private fun part2(assignmentPairs: List<Pair<IntRange, IntRange>>): Int {
    return assignmentPairs.count { (first, second) -> first.overlaps(second) || second.overlaps(first) }
}

// Checks for overlapping intervals by looking at the start and end points.
private fun IntRange.overlaps(other: IntRange) = first in other || last in other

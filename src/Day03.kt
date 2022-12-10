fun main() {
    val testInput = readInput("Day03_test")
    checkEquals(157, part1(testInput))
    checkEquals(70, part2(testInput))

    val input = readInput("Day03")
    println("Part 2: ${part2(input)}")
    println("Part 1: ${part1(input)}")
}

/**
 * Sums the priorities of items appearing in both compartments of each rucksack.
 */
private fun part1(input: List<String>): Int {
    return input.map { Rucksack(it) }
        .sumOf { it.findCommonItem().getPriority() }
}

private class Rucksack(input: String) {
    val compartment1: String
    val compartment2: String

    init {
        require(input.length % 2 == 0)
        compartment1 = input.substring(0 until input.length / 2)
        compartment2 = input.substring(input.length / 2)
    }

    fun findCommonItem(): Char {
        return compartment1.toSet().intersect(compartment2.toSet()).single()
    }
}

// This is split out into separate branches because [code] returns the UTF-16 code and lower and upper case letters do
// not occupy a contiguous interval in UTF-16.
private fun Char.getPriority() = when {
    this.isLowerCase() -> this.code - 'a'.code + 1
    else -> this.code - 'A'.code + 27
}

/**
 * Sums the priorities of the item types corresponding to the badge of each three-elf group.
 */
private fun part2(input: List<String>): Int {
    return input.chunked(3)
        .map { group ->
            group[0].toSet()
                .intersect(group[1].toSet())
                .intersect(group[2].toSet())
                .single()
        }
        .sumOf { it.getPriority() }
}

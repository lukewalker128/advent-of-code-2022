fun main() {
    /**
     * Returns the maximum number of calories held by a single elf.
     */
    fun part1(calories: List<Int>): Int {
        return calories.max()
    }

    /**
     * Returns the total number of calories held by the three elves carrying the most calories.
     */
    fun part2(calories: List<Int>): Int {
        // Instead of sorting the entire list it would be more efficient to scan the list and store the largest elements
        // in a heap of size 3, but the input size isn't large enough to warrant this.
        return calories.sortedDescending()
            .take(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testCalories = testInput.toCalories()
    check(part1(testCalories) == 24_000)
    check(part2(testCalories) == 45_000)

    val input = readInput("Day01")
    val calories = input.toCalories()
    println("Part 1: ${part1(calories)}")
    println("Part 2: ${part2(calories)}")
}

private fun List<String>.toCalories(): List<Int> {
    val calories = mutableListOf<Int>()
    var i = 0
    while (i < this.size) {
        // consume input up to the next blank line or until we reach the end of the file
        var count = 0
        while (i < this.size && this[i].isNotBlank()) {
            count += this[i].toInt()
            i++
        }
        calories.add(count)

        // skip over any blank lines in the input
        while (i < this.size && this[i].isBlank()) {
            i++
        }
    }

    return calories
}

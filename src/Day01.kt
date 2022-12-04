fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testCalories = testInput.toCalories()
    check(testCalories.max() == 24_000)

    val input = readInput("Day01")
    val calories = input.toCalories()
    println(calories.max())
}

private fun List<String>.toCalories(): List<Int> {
    val calories = mutableListOf<Int>()
    var count = 0
    for (line in this) {
        if (line.isNotBlank()) {
            count += line.toInt()
        } else {
            calories.add(count)
            count = 0
        }
    }

    return calories
}

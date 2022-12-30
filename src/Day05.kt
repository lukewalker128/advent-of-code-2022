fun main() {
    val testInput = readInput("Day05_test")
    checkEquals("CMZ", part1(testInput))
    checkEquals("MCD", part2(testInput))

    val input = readInput("Day05")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

/**
 * Execute commands from the given input on the initial stacks and output a combined string of the top crate on each
 * stack.
 */
private fun part1(input: List<String>): String {
    val (stacks, commands) = input.process()

    // execute the list of commands
    commands.forEach { command ->
        val src = stacks[command.srcStack - 1]
        val dest = stacks[command.destStack - 1]

        require(command.numCrates <= src.size)
        repeat(command.numCrates) {
            dest.add(src.removeLast())
        }
    }

    return stacks.map { it.last() }.joinToString(separator = "")
}

private fun List<String>.process(): Pair<List<MutableList<Char>>, List<Command>> {
    val partitionIndex = indexOfFirst { it.isBlank() }
    val stackLines = subList(0, partitionIndex)
    val commandLines = subList(partitionIndex + 1, size)

    // determine the number of stacks
    val numStacks = stackLines.last().split(" ").last().toInt()

    // initialize stacks from the input
    val stacks = List(numStacks) { mutableListOf<Char>() }
    for (i in stackLines.lastIndex - 1 downTo 0) {
        val line = stackLines[i]

        for (stackNum in 0 until numStacks) {
            val charIndex = stackNum * 4 + 1
            // need the bounds check since IJ removes trailing blank space from the input files
            if (charIndex < line.length && line[charIndex].isLetter()) {
                stacks[stackNum].add(line[charIndex])
            }
        }
    }

    // parse commands from the input
    val commands = commandLines.map { Command(it) }

    return Pair(stacks, commands)
}

private class Command(input: String) {
    val numCrates: Int
    val srcStack: Int
    val destStack: Int

    init {
        val match = requireNotNull(commandRegex.matchEntire(input))

        // first group is the entire substring matched
        match.groupValues.let {
            numCrates = it[1].toInt()
            srcStack = it[2].toInt()
            destStack = it[3].toInt()
        }
    }

    override fun toString(): String {
        return "num=$numCrates, src=$srcStack, dest=$destStack"
    }

    private companion object {
        val commandRegex = Regex("^move (\\d+) from (\\d+) to (\\d+)$")
    }
}

/**
 * Same as Part 1, but moves entire groups of crates instead of one at a time.
 */
private fun part2(input: List<String>): String {
    val (stacks, commands) = input.process()

    commands.forEach { command ->
        val src = stacks[command.srcStack - 1]
        val dest = stacks[command.destStack - 1]

        require(command.numCrates <= src.size)
        // NOTE: slice is used here instead of subList since the latter has undefined behavior when structural changes
        // are made to the base list.
        dest.addAll(src.slice(src.size - command.numCrates .. src.lastIndex))

        repeat(command.numCrates) {
            src.removeLast()
        }
    }

    return stacks.map { it.last() }.joinToString(separator = "")
}

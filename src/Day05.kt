
fun main() {
    fun getOriginalCrateStack(): Array<ArrayList<Char>> {
        return arrayOf(
            arrayListOf('G', 'T', 'R', 'W'),                        // 1
            arrayListOf('G', 'C', 'H', 'P', 'M', 'S', 'V', 'W'),    // 2
            arrayListOf('C', 'L', 'T', 'S', 'G', 'M'),              // 3
            arrayListOf('J', 'H', 'D', 'M', 'W', 'R', 'F'),         // 4
            arrayListOf('P', 'Q', 'L', 'H', 'S', 'W', 'F', 'J'),    // 5
            arrayListOf('P', 'J', 'D', 'N', 'F', 'M', 'S'),         // 6
            arrayListOf('Z', 'B', 'D', 'F', 'G', 'C', 'S', 'J'),    // 7
            arrayListOf('R', 'T', 'B'),                             // 8
            arrayListOf('H', 'N', 'W', 'L', 'C')                    // 9
        )
    }


    fun moveCrates(crateStacks: Array<ArrayList<Char>>, cratesToMove: Int, fromStackPos: Int, toStackPos: Int) {
        for (i in 0 until cratesToMove) {
            val fromStack = crateStacks[fromStackPos - 1]
            val toStack = crateStacks[toStackPos - 1]
            toStack += fromStack.removeLast()
        }
    }

    fun moveCrateStacks(crateStacks: Array<ArrayList<Char>>, cratesToMove: Int, fromStackPos: Int, toStackPos: Int) {
        val fromStack = crateStacks[fromStackPos - 1]
        val toStack = crateStacks[toStackPos - 1]
        toStack += fromStack.takeLast(cratesToMove)
        for (i in 0 until cratesToMove) {
            fromStack.removeLast()
        }
    }

    val CRATES_TO_MOVE_ARG = 1
    val FROM_STACK_ARG = 3
    val TO_STACK_ARG = 5

    fun part1(input: List<String>): String {
        val crateStack = getOriginalCrateStack()
        input.forEach {
            val args = it.split(' ')
            moveCrates(crateStack, args[CRATES_TO_MOVE_ARG].toInt(), args[FROM_STACK_ARG].toInt(), args[TO_STACK_ARG].toInt())
        }
        return crateStack.map { it.last() }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val crateStack = getOriginalCrateStack()
        input.forEach {
            val args = it.split(' ')
            moveCrateStacks(crateStack, args[CRATES_TO_MOVE_ARG].toInt(), args[FROM_STACK_ARG].toInt(), args[TO_STACK_ARG].toInt())
        }
        return crateStack.map { it.last() }.joinToString("")
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
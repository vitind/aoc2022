
fun main() {
    val ARRAY_SIZE = 52
    val ARRAY_OFFSET = 26

    fun getIndexedCharArray(s: String) : Array<Int> {
        val indexedCharArray = Array<Int>(ARRAY_SIZE){0}
        s.forEach {
            val index = if (it.isLowerCase()) {
                it.minus('a')
            } else {    // is upper case
                it.minus('A') + ARRAY_OFFSET
            }
            indexedCharArray[index] += 1
        }
        return indexedCharArray
    }

    fun part1(input: List<String>): Int {
        var totalPriority = 0
        input.forEach { line ->
            val halfLength = line.length / 2
            val firstCompartment = line.slice(0 until halfLength)
            val secondCompartment = line.slice(halfLength until line.length)
            val firstCount = getIndexedCharArray(firstCompartment)
            val secondCount = getIndexedCharArray(secondCompartment)
            for (index in 0 until ARRAY_SIZE) {
                if ((firstCount[index] > 0) && (secondCount[index] > 0)) {
                    totalPriority += (index + 1)
                }
            }
        }
        return totalPriority
    }

    fun part2(input: List<String>): Int {
        var totalPriority = 0
        val LINE_GROUPS = 3
        for (index in input.indices step LINE_GROUPS) {
            val firstCount = getIndexedCharArray(input[index])
            val secondCount = getIndexedCharArray(input[index + 1])
            val thirdCount = getIndexedCharArray(input[index + 2])
            for (countIndex in 0 until ARRAY_SIZE) {
                if ((firstCount[countIndex] > 0) && (secondCount[countIndex] > 0) && (thirdCount[countIndex] > 0)) {
                    totalPriority += (countIndex + 1)
                }
            }
        }
        return totalPriority
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
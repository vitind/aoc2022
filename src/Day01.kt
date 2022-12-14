fun main() {
    fun part1(input: List<String>): Int {
        var highestCalories = 0
        input.fold(0) { totalCalories, value ->
            if (value.isNotEmpty()) {
                totalCalories + value.toInt()
            } else {    // value is empty -> check if it is the highest calories
                if (highestCalories < totalCalories) {
                    highestCalories = totalCalories
                }
                0
            }
        }
        return highestCalories
    }

    fun part2(input: List<String>): Int {
        val allTotalCalories = arrayListOf<Int>()
        input.fold(0) { totalCalories, value ->
            if (value.isNotEmpty()) {
                totalCalories + value.toInt()
            } else {
                allTotalCalories.add(totalCalories)
                0
            }
        }
        return allTotalCalories.sortedDescending().take(3).sum()
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

// Part 1 Scoring
// A = Rock         (Get 1 point for choosing)
// B = Paper        (Get 2 point for choosing)
// C = Scissors     (Get 3 point for choosing)

// X = Rock
// Y = Paper
// Z = Scissors

// Lose = 0 points
// Draw = 3 points
// Win = 6 points

// A X = Rock Rock + Draw = 1 + 3 = 4
// A Y = Rock Paper + Win = 2 + 6 = 8
// A Z = Rock Scissors + Lose = 3 + 0 = 3
// B X = Paper Rock + Lose = 1 + 0 = 1
// B Y = Paper Paper + Draw = 2 + 3 = 5
// B Z = Paper Scissors + Win = 3 + 6 = 9
// C X = Scissors Rock + Win = 1 + 6 = 7
// C Y = Scissors Paper + Lose = 2 + 0 = 2
// C Z = Scissors Scissors + Draw = 3 + 3 = 6

// Part 2 Scoring
// A = Rock         (Get 1 point for choosing)
// B = Paper        (Get 2 point for choosing)
// C = Scissors     (Get 3 point for choosing)

// X = Lose
// Y = Draw
// Z = Win

// Lose = 0 points
// Draw = 3 points
// Win = 6 points

// A X = Rock Scissors + Lose = 3 + 0 = 3
// A Y = Rock Rock + Draw = 1 + 3 = 4
// A Z = Rock Paper + Win = 2 + 6 = 8
// B X = Paper Rock + Lose = 1 + 0 = 1
// B Y = Paper Paper + Draw = 2 + 3 = 5
// B Z = Paper Scissors + Win = 3 + 6 = 9
// C X = Scissors Paper + Lose = 2 + 0 = 2
// C Y = Scissors Scissors + Draw = 3 + 3 = 6
// C Z = Scissors Rock + Win = 1 + 6 = 7

fun main() {
    fun part1(input: List<String>): Int {
        val finalScore = input.fold(0) { totalScore, strategy ->
            val score = when (strategy) {
                "A X" -> 4
                "A Y" -> 8
                "A Z" -> 3
                "B X" -> 1
                "B Y" -> 5
                "B Z" -> 9
                "C X" -> 7
                "C Y" -> 2
                "C Z" -> 6
                else -> 0
            }
            totalScore + score
        }
        return finalScore
    }
    fun part2(input: List<String>): Int {
        val finalScore = input.fold(0) { totalScore, strategy ->
            val score = when (strategy) {
                "A X" -> 3
                "A Y" -> 4
                "A Z" -> 8
                "B X" -> 1
                "B Y" -> 5
                "B Z" -> 9
                "C X" -> 2
                "C Y" -> 6
                "C Z" -> 7
                else -> 0
            }
            totalScore + score
        }
        return finalScore
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
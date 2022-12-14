
fun main() {
    data class MonkeyPart1(var items: ArrayList<Int>, var operation: (Int) -> Int, var test: (Int) -> Int, var inspectedCount: Int = 0)
    data class MonkeyPart2(var items: ArrayList<Int>, var operation: (Int) -> Pair<Int,Int>, var inspectedCount: Int = 0)

    fun getMonkeyStartStatePart1() : ArrayList<MonkeyPart1> {
        return arrayListOf<MonkeyPart1>(
            MonkeyPart1( // 0
                arrayListOf(93, 54, 69, 66, 71),
                { it * 3 },
                { if ((it % 7) == 0) 7 else 1}
            ),
            MonkeyPart1( // 1
                arrayListOf(89, 51, 80, 66),
                { it * 17 },
                { if ((it % 19) == 0) 5 else 7}
            ),
            MonkeyPart1( // 2
                arrayListOf(90, 92, 63, 91, 96, 63, 64),
                { it + 1 },
                { if ((it % 13) == 0) 4 else 3}
            ),
            MonkeyPart1( // 3
                arrayListOf(65, 77),
                { it + 2 },
                { if ((it % 3) == 0) 4 else 6}
            ),
            MonkeyPart1( // 4
                arrayListOf(76, 68, 94),
                { it * it },
                { if ((it % 2) == 0) 0 else 6}
            ),
            MonkeyPart1( // 5
                arrayListOf(86, 65, 66, 97, 73, 83),
                { it + 8 },
                { if ((it % 11) == 0) 2 else 3}
            ),
            MonkeyPart1( // 6
                arrayListOf(78),
                { it + 6 },
                { if ((it % 17) == 0) 0 else 1}
            ),
            MonkeyPart1( // 7
                arrayListOf(89, 57, 59, 61, 87, 55, 55, 88),
                { it + 7 },
                { if ((it % 5) == 0) 2 else 5}
            ),
        )
    }

    // LCM modulo value maintains the value from causing overflow or calculating values that grow exponentially
    val modValue = 7 * 19 * 13 * 3 * 2 * 11 * 17 * 5
    fun getMonkeyStartStatePart2() : ArrayList<MonkeyPart2> {
        return arrayListOf<MonkeyPart2>(
            MonkeyPart2( // 0
                arrayListOf(93, 54, 69, 66, 71),
                {
                    val newWorry = ((it.toLong() * 3) % modValue).toInt()
                    val monkeyIndex = if ((newWorry % 7) == 0) 7 else 1
                    Pair(newWorry, monkeyIndex)
                }
            ),
            MonkeyPart2( // 1
                arrayListOf(89, 51, 80, 66),
                {
                    val newWorry = ((it.toLong() * 17) % modValue).toInt()
                    val monkeyIndex = if ((newWorry % 19) == 0) 5 else 7
                    Pair(newWorry, monkeyIndex)
                }
            ),
            MonkeyPart2( // 2
                arrayListOf(90, 92, 63, 91, 96, 63, 64),
                {
                    val newWorry = ((it.toLong() + 1) % modValue).toInt()
                    val monkeyIndex = if ((newWorry % 13) == 0) 4 else 3
                    Pair(newWorry, monkeyIndex)
                }
            ),
            MonkeyPart2( // 3
                arrayListOf(65, 77),
                {
                    val newWorry = ((it.toLong() + 2) % modValue).toInt()
                    val monkeyIndex = if ((newWorry % 3) == 0) 4 else 6
                    Pair(newWorry, monkeyIndex)
                }
            ),
            MonkeyPart2( // 4
                arrayListOf(76, 68, 94),
                {
                    val newWorry = ((it.toLong() * it.toLong()) % modValue).toInt()
                    val monkeyIndex = if ((newWorry % 2) == 0) 0 else 6
                    Pair(newWorry % modValue, monkeyIndex)
                }
            ),
            MonkeyPart2( // 5
                arrayListOf(86, 65, 66, 97, 73, 83),
                {
                    val newWorry = ((it.toLong() + 8) % modValue).toInt()
                    val monkeyIndex = if ((newWorry % 11) == 0) 2 else 3
                    Pair(newWorry, monkeyIndex)
                }
            ),
            MonkeyPart2( // 6
                arrayListOf(78),
                {
                    val newWorry = ((it + 6).toLong() % modValue).toInt()
                    val monkeyIndex = if ((newWorry % 17) == 0) 0 else 1
                    Pair(newWorry, monkeyIndex)
                }
            ),
            MonkeyPart2( // 7
                arrayListOf(89, 57, 59, 61, 87, 55, 55, 88),
                {
                    val newWorry = ((it + 7).toLong() % modValue).toInt()
                    val monkeyIndex = if ((newWorry % 5) == 0) 2 else 5
                    Pair(newWorry, monkeyIndex)
                }
            ),
        )
    }

    fun part1(input: List<String>): Int {
        val monkies = getMonkeyStartStatePart1()
        (0 until 20).forEach {
            for (monkey in monkies) {
                while (monkey.items.isNotEmpty()) {
                    val item = monkey.items.removeFirst()
                    val newWorry = (monkey.operation(item) / 3).toInt()     // Monkey inspected the item
                    monkey.inspectedCount += 1
                    val passToMonkeyIndex = monkey.test(newWorry)
                    monkies[passToMonkeyIndex].items.add(newWorry)
                }
            }
        }
        val inspectionCounts = monkies.map { monkey -> monkey.inspectedCount }.toMutableSet()
        val firstMax = inspectionCounts.max()
        inspectionCounts.remove(firstMax)
        val secondMax = inspectionCounts.max()
        return firstMax * secondMax
    }

    fun part2(input: List<String>): Long {
        val monkies = getMonkeyStartStatePart2()
        (0 until 10000).forEach {
            for (monkey in monkies) {
                while (monkey.items.isNotEmpty()) {
                    val item = monkey.items.removeFirst()
                    val result = monkey.operation(item)
                    monkey.inspectedCount += 1
                    monkies[result.second].items.add((result.first))          // Pair<Long,Int> = Pair<Worry,MonkeyIndex>
                }
            }
        }
        val inspectionCounts = monkies.map { monkey -> monkey.inspectedCount }.toMutableSet()
        val firstMax = inspectionCounts.max()
        inspectionCounts.remove(firstMax)
        val secondMax = inspectionCounts.max()
        return firstMax.toLong() * secondMax.toLong()
    }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
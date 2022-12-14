
fun main() {
    fun Boolean.toInt() = if (this) 1 else 0

    fun displayGridCheck(gridCheck: ArrayList<Array<Boolean>>) {
        for (y in 0 until gridCheck.size) {
            for (x in 0 until gridCheck[y].size) {
                print(gridCheck[y][x].toInt())
            }
            println()
        }
    }

    fun initVisibleTreeCount(gridCheck: ArrayList<Array<Boolean>>) {
        // Outside border of trees are all visible
        gridCheck[0] = Array(gridCheck[0].size) { true }                    // Top row
        gridCheck[gridCheck.size - 1] = Array(gridCheck[0].size) { true }   // Bottom row
        for (y in 1 until gridCheck.size - 1) {
            gridCheck[y][0] = true
            gridCheck[y][gridCheck[y].size - 1] = true
        }
    }

    fun getVisibleTreeCount(gridCheck: ArrayList<Array<Boolean>>, gridValues: ArrayList<Array<Byte>>) : Int {
        // Check from left
        for (y in 1 until gridValues.size - 1) {
            var nextTallestTree = gridValues[y][0]
            for (x in 1 until gridValues[y].size - 1) {
                val nextTree = gridValues[y][x]
                if (nextTallestTree < nextTree) {
                    nextTallestTree = nextTree
                    gridCheck[y][x] = true
                }
            }
        }
        // Check from right
        for (y in (1 until gridValues.size - 1).reversed()) {
            var nextTallestTree = gridValues[y][gridValues.size - 1]
            for (x in (1 until gridValues[y].size - 1).reversed()) {
                val nextTree = gridValues[y][x]
                if (nextTallestTree < nextTree) {
                    nextTallestTree = nextTree
                    gridCheck[y][x] = true
                }
            }
        }
        // Check from top
        for (x in 1 until gridValues[0].size - 1) {
            var nextTallestTree = gridValues[0][x]
            for (y in 1 until gridValues.size - 1) {
                val nextTree = gridValues[y][x]
                if (nextTallestTree < nextTree) {
                    nextTallestTree = nextTree
                    gridCheck[y][x] = true
                }
            }
        }
        // Check from bottom
        for (x in (1 until gridValues[0].size - 1).reversed()) {
            var nextTallestTree = gridValues[gridValues.size - 1][x]
            for (y in (1 until gridValues.size - 1).reversed()) {
                val nextTree = gridValues[y][x]
                if (nextTallestTree < nextTree) {
                    nextTallestTree = nextTree
                    gridCheck[y][x] = true
                }
            }
        }
        return gridCheck.sumOf { it.sumOf { it.toInt() } }
    }

    fun scenicScoreAtPos(gridValues: ArrayList<Array<Byte>>, xPos: Int, yPos: Int) : Int {
        val treeHeightAtPos = gridValues[yPos][xPos]
        var treesVisibleToLeft = 0
        var treesVisibleToRight = 0
        var treesVisibleToTop = 0
        var treesVisibleToBottom = 0

        // To the left from position
        for (x in (0..xPos - 1).reversed()) {
            val nextTree = gridValues[yPos][x].toInt()
            if (nextTree < treeHeightAtPos) {
                treesVisibleToLeft += 1
            } else {
                treesVisibleToLeft += 1
                break
            }
        }

        // To the right from position
        for (x in xPos + 1 until gridValues[yPos].size) {
            val nextTree = gridValues[yPos][x].toInt()
            if (nextTree < treeHeightAtPos) {
                treesVisibleToRight += 1
            } else {
                treesVisibleToRight += 1
                break
            }
        }

        // To the top from position
        for (y in (0..yPos - 1).reversed()) {
            val nextTree = gridValues[y][xPos].toInt()
            if (nextTree < treeHeightAtPos) {
                treesVisibleToTop += 1
            } else {
                treesVisibleToTop += 1
                break
            }
        }

        // To the bottom from position
        for (y in yPos + 1 until gridValues.size) {
            val nextTree = gridValues[y][xPos].toInt()
            if (nextTree < treeHeightAtPos) {
                treesVisibleToBottom += 1
            } else {
                treesVisibleToBottom += 1
                break
            }
        }
        val scenicValue = treesVisibleToLeft * treesVisibleToRight * treesVisibleToTop * treesVisibleToBottom
        return scenicValue
    }

    fun part1(input: List<String>): Int {
        val gridCheck = arrayListOf<Array<Boolean>>()
        val gridValues = arrayListOf<Array<Byte>>()
        input.forEach { line ->
            gridCheck.add(Array(line.length) { false })
            gridValues.add(line.map { it.digitToInt().toByte() }.toTypedArray())
        }
        initVisibleTreeCount(gridCheck)
        return getVisibleTreeCount(gridCheck, gridValues)
    }

    fun part2(input: List<String>): Int {
        val gridCheck = arrayListOf<Array<Boolean>>()
        val gridValues = arrayListOf<Array<Byte>>()
        input.forEach { line ->
            gridCheck.add(Array(line.length) { false })
            gridValues.add(line.map { it.digitToInt().toByte() }.toTypedArray())
        }
        val highestScenicValue = (0 until gridValues.size).maxOf { yPos ->
            (0 until gridValues[0].size).maxOf { xPos ->
                scenicScoreAtPos(gridValues, xPos, yPos)
            }
        }
        return highestScenicValue
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
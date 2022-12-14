typealias RockPoint = Pair<Int,Int>     // X, Y

fun main() {
    val X_COORDS = 0
    val Y_COORDS = 1

    fun getMaxYDimension(allRockPoints: Set<RockPoint>) : Int {
        return allRockPoints.maxOf { it.second }
    }

    fun expandRockPoints(allRockPoints: MutableSet<RockPoint>, rockPointsList: List<List<RockPoint>>) {
        rockPointsList.forEach {
            for (rockPointIndex in 0 until it.size - 1) {
                val firstRockPoint = it[rockPointIndex]
                val secondRockPoint = it[rockPointIndex + 1]
                val xDist = firstRockPoint.first - secondRockPoint.first
                val yDist = firstRockPoint.second - secondRockPoint.second
                if (xDist != 0) {
                    var xPos = firstRockPoint.first
                    val yPos = firstRockPoint.second
                    if (xDist > 0) {
                        do {
                            val rockPoint = RockPoint(xPos, yPos)
                            allRockPoints.add(rockPoint)
                            xPos -= 1
                        } while (rockPoint != secondRockPoint)
                    } else {    // xDist < 0
                        do {
                            val rockPoint = RockPoint(xPos, yPos)
                            allRockPoints.add(rockPoint)
                            xPos += 1
                        } while (rockPoint != secondRockPoint)
                    }
                } else if (yDist != 0) {
                    val xPos = firstRockPoint.first
                    var yPos = firstRockPoint.second
                    if (yDist > 0) {
                        do {
                            val rockPoint = RockPoint(xPos, yPos)
                            allRockPoints.add(rockPoint)
                            yPos -= 1
                        } while (rockPoint != secondRockPoint)
                    } else {    // yDist < 0
                        do {
                            val rockPoint = RockPoint(xPos, yPos)
                            allRockPoints.add(rockPoint)
                            yPos += 1
                        } while (rockPoint != secondRockPoint)
                    }
                } else {
                    println("Warning! This should not occur!")
                }
            }
        }
    }

    fun initRockPointsList(input: List<String>) : List<List<RockPoint>> {
        return input.map { it.split(" -> ") }.map {
            it.map {
                val coords = it.split(",")
                RockPoint(coords[X_COORDS].toInt(), coords[Y_COORDS].toInt())
            }
        }
    }

    val SAND_START_X_POS = 500
    val SAND_START_Y_POS = 0
    fun getFallingSandUnitCount(allRockPoints: MutableSet<RockPoint>) : Int {
        // Sand is falling from (500, 0)
        var totalSandUnits = 0

        val abyssYPos = getMaxYDimension(allRockPoints)           // Sand must reach past this Y pos to reach abyss
        var abyssReached = false
        while (!abyssReached) {
            var sandXPos = SAND_START_X_POS
            var sandYPos = SAND_START_Y_POS
            var stillFalling = true
            while (stillFalling) {
                val isPosBelowBlocked = allRockPoints.contains(RockPoint(sandXPos, sandYPos + 1))
                if (!isPosBelowBlocked) {
                    sandYPos += 1
                    // Special case - Check if the abyss has been reached -> break out of the loop and set abyssReached = true
                    if (sandYPos > abyssYPos) {
                        stillFalling = false
                        abyssReached = true
                    }
                    continue
                }
                val isPosBelowLeftBlocked = allRockPoints.contains(RockPoint(sandXPos - 1, sandYPos + 1))
                if (!isPosBelowLeftBlocked) {
                    sandXPos -= 1
                    sandYPos += 1
                    continue
                }
                val isPosBelowRightBlocked = allRockPoints.contains(RockPoint(sandXPos + 1, sandYPos + 1))
                if (!isPosBelowRightBlocked) {
                    sandXPos += 1
                    sandYPos += 1
                    continue
                }
                // Sand reached the end -> add the sand as a rock point
                allRockPoints.add(RockPoint(sandXPos, sandYPos))
                totalSandUnits += 1
                stillFalling = false
            }
        }

        return totalSandUnits
    }

    fun getFallingSandUnitCountWithFloor(allRockPoints: MutableSet<RockPoint>) : Int {
        // Sand is falling from (500, 0)
        var totalSandUnits = 0

        val floorYPos = getMaxYDimension(allRockPoints) + 2     // The floor is located at the highest Y dimension plus 2
        var sandIsStillMoving = true
        while (sandIsStillMoving) {
            var sandXPos = SAND_START_X_POS
            var sandYPos = SAND_START_Y_POS
            var stillFalling = true
            while (stillFalling) {
                // Special case - check if the (sandYPos + 1) == (floorYPos) -> Add the floor rock points to all rock points set
                if ((sandYPos + 1) == floorYPos) {
                    allRockPoints.add(RockPoint(sandXPos, sandYPos + 1))
                    allRockPoints.add(RockPoint(sandXPos - 1, sandYPos + 1))
                    allRockPoints.add(RockPoint(sandXPos + 1, sandYPos + 1))
                }
                val isPosBelowBlocked = allRockPoints.contains(RockPoint(sandXPos, sandYPos + 1))
                if (!isPosBelowBlocked) {
                    sandYPos += 1
                    continue
                }
                val isPosBelowLeftBlocked = allRockPoints.contains(RockPoint(sandXPos - 1, sandYPos + 1))
                if (!isPosBelowLeftBlocked) {
                    sandXPos -= 1
                    sandYPos += 1
                    continue
                }
                val isPosBelowRightBlocked = allRockPoints.contains(RockPoint(sandXPos + 1, sandYPos + 1))
                if (!isPosBelowRightBlocked) {
                    sandXPos += 1
                    sandYPos += 1
                    continue
                }
                // Sand reached the end -> check if the sand has not moved else add the sand as a rock point
                if ((sandXPos == SAND_START_X_POS) && (sandYPos == SAND_START_Y_POS)) {
                    sandIsStillMoving = false
                } else {
                    allRockPoints.add(RockPoint(sandXPos, sandYPos))
                }
                totalSandUnits += 1
                stillFalling = false
            }
        }

        return totalSandUnits
    }

    fun part1(input: List<String>): Int {
        val allRockPoints = mutableSetOf<RockPoint>()
        val rockPointsList = initRockPointsList(input)
        expandRockPoints(allRockPoints, rockPointsList)
        return getFallingSandUnitCount(allRockPoints)
    }

    fun part2(input: List<String>): Int {
        val allRockPoints = mutableSetOf<RockPoint>()
        val rockPointsList = initRockPointsList(input)
        expandRockPoints(allRockPoints, rockPointsList)
        return getFallingSandUnitCountWithFloor(allRockPoints)
    }

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
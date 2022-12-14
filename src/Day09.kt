import kotlin.math.absoluteValue
import kotlin.system.exitProcess

fun main() {
    data class Position(var x: Int, var y: Int)

    val DIRECTION = 0
    val MOVE_AMOUNT = 1

    fun updateTailPosition(tailMovementHistory: ArrayList<Position>, headPos: Position, tailPos: Position) {
        val xDistance = (headPos.x - tailPos.x).absoluteValue
        val yDistance = (headPos.y - tailPos.y).absoluteValue
        if ((xDistance <= 1) && (yDistance <= 1)) {
            // Do nothing
        } else {    // We must move!
            if (((xDistance == 2) && (yDistance == 1)) || ((xDistance == 1) && (yDistance == 2))) {   // (xDist = 2, yDist = 1), (xDist = 1, yDist = 2) -> Move diagonally
                // Find horizontal direction
                if (headPos.x < tailPos.x) {      // Move left
                    tailPos.x -= 1
                } else {                                  // Move right
                    tailPos.x += 1
                }
                // Find vertical direction
                if (headPos.y < tailPos.y) {      // Move down
                    tailPos.y -= 1
                } else {                                    // Move up
                    tailPos.y += 1
                }
            } else if ((xDistance == 2) && (yDistance == 0)) {   // xDist = 2, yDist = 0 -> Move horizonatlly
                // Find horizontal direction
                if (headPos.x < tailPos.x) {      // Move left
                    tailPos.x -= 1
                } else {                                  // Move right
                    tailPos.x += 1
                }
            } else if ((xDistance == 0) && (yDistance == 2)) {   // xDist = 0, yDist = 2 -> Move vertically
                // Find vertical direction
                if (headPos.y < tailPos.y) {      // Move down
                    tailPos.y -= 1
                } else {                                    // Move up
                    tailPos.y += 1
                }
            }
            tailMovementHistory.add(tailPos.copy())
        }
    }

    fun moveHeadPositionPart1(tailMovementHistory: ArrayList<Position>, headPos: Position, tailPos: Position, moveLine: String) {
        val moveVector = moveLine.split(" ")
        val moveDirection = moveVector[DIRECTION]
        val moveAmount = moveVector[MOVE_AMOUNT].toInt()
        when (moveDirection) {
            "L" -> {
                (0 until moveAmount).map {
                    headPos.x -= 1
                    updateTailPosition(tailMovementHistory, headPos, tailPos)
                }
            }
            "R" -> {
                (0 until moveAmount).map {
                    headPos.x += 1
                    updateTailPosition(tailMovementHistory, headPos, tailPos)
                }
            }
            "U" -> {
                (0 until moveAmount).map {
                    headPos.y += 1
                    updateTailPosition(tailMovementHistory, headPos, tailPos)
                }
            }
            "D" -> {
                (0 until moveAmount).map {
                    headPos.y -= 1
                    updateTailPosition(tailMovementHistory, headPos, tailPos)
                }
            }
        }
    }

    fun updateKnotsPosition(firstKnot: Position, secondKnot: Position) {
        val xDistance = (firstKnot.x - secondKnot.x).absoluteValue
        val yDistance = (firstKnot.y - secondKnot.y).absoluteValue
        if ((xDistance <= 1) && (yDistance <= 1)) {
            // Do nothing
        } else {    // We must move!
            if (((xDistance > 1) && (yDistance == 1)) || ((xDistance == 1) && (yDistance > 1)) || ((xDistance > 1) && (yDistance > 1))) {   // (xDist = 2, yDist = 1), (xDist = 1, yDist = 2) -> Move diagonally
                // Find horizontal direction
                if (firstKnot.x < secondKnot.x) {      // Move left
                    secondKnot.x -= 1
                } else {                                  // Move right
                    secondKnot.x += 1
                }
                // Find vertical direction
                if (firstKnot.y < secondKnot.y) {      // Move down
                    secondKnot.y -= 1
                } else {                                    // Move up
                    secondKnot.y += 1
                }
            } else if ((xDistance > 1) && (yDistance == 0)) {   // xDist = 2, yDist = 0 -> Move horizonatlly
                // Find horizontal direction
                if (firstKnot.x < secondKnot.x) {      // Move left
                    secondKnot.x -= 1
                } else {                                  // Move right
                    secondKnot.x += 1
                }
            } else if ((xDistance == 0) && (yDistance > 1)) {   // xDist = 0, yDist = 2 -> Move vertically
                // Find vertical direction
                if (firstKnot.y < secondKnot.y) {      // Move down
                    secondKnot.y -= 1
                } else {                                    // Move up
                    secondKnot.y += 1
                }
            } else {
                println("ERROR: This should never occur!")
                exitProcess(-1)
            }
        }
    }

    val HEAD_POSITION = 0
    val TAIL_POSITION = 9
    fun moveHeadPositionPart2(tailMovementHistory: ArrayList<Position>, positions: ArrayList<Position>, moveLine: String) {
        val moveVector = moveLine.split(" ")
        val moveDirection = moveVector[DIRECTION]
        val moveAmount = moveVector[MOVE_AMOUNT].toInt()
        when (moveDirection) {
            "L" -> {
                (0 until moveAmount).map {
                    positions[HEAD_POSITION].x -= 1
                    (0 until positions.size - 1).forEach {
                        updateKnotsPosition(positions[it], positions[it + 1])
                    }
                    tailMovementHistory.add(positions[TAIL_POSITION].copy())
                }
            }
            "R" -> {
                (0 until moveAmount).map {
                    positions[HEAD_POSITION].x += 1
                    (0 until positions.size - 1).forEach {
                        updateKnotsPosition(positions[it], positions[it + 1])
                    }
                    tailMovementHistory.add(positions[TAIL_POSITION].copy())
                }
            }
            "U" -> {
                (0 until moveAmount).map {
                    positions[HEAD_POSITION].y += 1
                    (0 until positions.size - 1).forEach {
                        updateKnotsPosition(positions[it], positions[it + 1])
                    }
                    tailMovementHistory.add(positions[TAIL_POSITION].copy())
                }
            }
            "D" -> {
                (0 until moveAmount).map {
                    positions[HEAD_POSITION].y -= 1
                    (0 until positions.size - 1).forEach {
                        updateKnotsPosition(positions[it], positions[it + 1])
                    }
                    tailMovementHistory.add(positions[TAIL_POSITION].copy())
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        val headPos = Position(0,0)        // X,Y
        val tailPos = Position(0,0)        // X,Y
        val tailMovementHistory = arrayListOf<Position>()
        tailMovementHistory.add(Position(0,0))
        input.forEach {
            moveHeadPositionPart1(tailMovementHistory, headPos, tailPos, it)
        }
        return tailMovementHistory.distinctBy { Pair(it.x, it.y) }.size
    }

    fun part2(input: List<String>): Int {
        val positions = arrayListOf<Position>()
        (0 until 10).forEach { _ -> positions.add(Position(0,0)) }
        val tailMovementHistory = arrayListOf<Position>()
        tailMovementHistory.add(Position(0,0))
        input.forEach {
            moveHeadPositionPart2(tailMovementHistory, positions, it)
        }
        return tailMovementHistory.distinctBy { Pair(it.x, it.y) }.size
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
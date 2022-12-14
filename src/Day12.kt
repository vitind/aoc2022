import java.util.*
import kotlin.math.absoluteValue

fun main() {
    data class Node(val position: Pair<Int,Int>, val cost: Int, val estimate: Int, val parentNode: Node? = null)

    fun loadGrid(input: List<String>) = input.map { it.toCharArray() }
    fun getLetter(grid: List<CharArray>, xPos: Int, yPos: Int) = grid[yPos][xPos]
    fun getLetter(grid: List<CharArray>, position: Pair<Int,Int>) = grid[position.second][position.first]
    fun findPosition(grid: List<CharArray>, ch: Char) : Pair<Int,Int> {
        for (yPos in grid.indices) {
            for (xPos in 0 until grid[yPos].size) {
                if (grid[yPos][xPos] == ch) {
                    return Pair(xPos, yPos)
                }
            }
        }
        return Pair(-1,-1)
    }

    fun findStartPosition(grid: List<CharArray>) : Pair<Int,Int> {       // xPos, yPos
        return findPosition(grid, 'S')
    }

    fun findEndPosition(grid: List<CharArray>) : Pair<Int, Int> {
        return findPosition(grid, 'E')
    }

    fun getHeuristicDistance(firstPosition: Pair<Int,Int>, secondPosition: Pair<Int,Int>) : Int {
        // Manhattan distance
        return (firstPosition.first - secondPosition.first).absoluteValue + (firstPosition.second - secondPosition.second).absoluteValue
    }

    fun isPositionVisited(positionVisited: MutableSet<Pair<Int,Int>>, position: Pair<Int,Int>) : Boolean = positionVisited.contains(position)

    fun addVisitedPosition(positionVisited: MutableSet<Pair<Int,Int>>, position: Pair<Int,Int>) {
        positionVisited.add(position)
    }

    fun isValidMove(currentLetter: Char, nextLetter: Char) : Boolean = if ((nextLetter.code <= (currentLetter.code + 1))) true else false

    fun expandNode(open: PriorityQueue<Node>, positionVisited: MutableSet<Pair<Int, Int>>, grid: List<CharArray>, endPosition: Pair<Int,Int>, node: Node) {
        val xPos = node.position.first
        val yPos = node.position.second
        val letter = getLetter(grid, xPos, yPos)
        // Left
        if ((xPos - 1) >= 0) {
            val newPosition = Pair(xPos - 1, yPos)
            val newLetter = getLetter(grid, newPosition)
            if (isValidMove(letter, newLetter) && !isPositionVisited(positionVisited, newPosition)) {
                open.add(Node(newPosition, node.cost + 1, getHeuristicDistance(newPosition, endPosition), node))
            }
        }
        // Right
        if ((xPos + 1) < grid[0].size) {
            val newPosition = Pair(xPos + 1, yPos)
            val newLetter = getLetter(grid, newPosition)
            if (isValidMove(letter, newLetter) && !isPositionVisited(positionVisited, newPosition)) {
                open.add(Node(newPosition, node.cost + 1, getHeuristicDistance(newPosition, endPosition), node))
            }
        }
        // Up
        if ((yPos - 1) >= 0) {
            val newPosition = Pair(xPos, yPos - 1)
            val newLetter = getLetter(grid, newPosition)
            if (isValidMove(letter, newLetter) && !isPositionVisited(positionVisited, newPosition)) {
                open.add(Node(newPosition, node.cost + 1, getHeuristicDistance(newPosition, endPosition), node))
            }
        }
        // Down
        if ((yPos + 1) < grid.size) {
            val newPosition = Pair(xPos, yPos + 1)
            val newLetter = getLetter(grid, newPosition)
            if (isValidMove(letter, newLetter) && !isPositionVisited(positionVisited, newPosition)) {
                open.add(Node(newPosition, node.cost + 1, getHeuristicDistance(newPosition, endPosition), node))
            }
        }
    }

    fun displayGrid(grid: List<CharArray>) {
        for (gridLine in grid) {
            println(gridLine)
        }
    }

    fun part1(input: List<String>): Int {
        val grid = loadGrid(input)
        val startPosition = findStartPosition(grid)
        val endPosition = findEndPosition(grid)
        val positionVisited = mutableSetOf<Pair<Int,Int>>()
        // A* search algorithm with Manhattan distance heuristic
        val open = PriorityQueue<Node>(1000, compareBy { it.cost + it.estimate })
        open.add(Node(startPosition, 0, getHeuristicDistance(startPosition, endPosition)))

        // Change the grid positions to be their elevations
        grid[startPosition.second][startPosition.first] = 'a'
        grid[endPosition.second][endPosition.first] = 'z'

        var cost = 0
        while (open.isNotEmpty()) {
            val node = open.remove()
            if (node.position.equals(endPosition)) {
//                println("End Goal Found | Cost = ${node.cost}")
                cost = node.cost
                break
            }
            addVisitedPosition(positionVisited, node.position)
            expandNode(open, positionVisited, grid, endPosition, node)
        }
        return cost
    }

    fun part2(input: List<String>): Int {
        val grid = loadGrid(input)
        val startPosition = findStartPosition(grid)
        val endPosition = findEndPosition(grid)
        val positionVisited = mutableSetOf<Pair<Int,Int>>()
        // A* search algorithm with Manhattan distance heuristic
        val open = PriorityQueue<Node>(1000, compareBy { it.cost + it.estimate })

        // Change the grid positions to be their elevations
        grid[startPosition.second][startPosition.first] = 'a'
        grid[endPosition.second][endPosition.first] = 'z'

        // Find all the positions of 'a'
        val aPositions = arrayListOf<Pair<Int,Int>>()
        for (yPos in grid.indices) {
            for (xPos in 0 until grid[yPos].size) {
                val position = Pair(xPos, yPos)
                if (getLetter(grid, position) == 'a') {
                    aPositions.add(position)
                }
            }
        }

        //
        var shortestCost = Int.MAX_VALUE
        var shortestPosition = Pair(-1,-1)
        while (aPositions.isNotEmpty()) {
            val aPosition = aPositions.removeFirst()
            open.add(Node(aPosition, 0, getHeuristicDistance(aPosition, endPosition)))
            while (open.isNotEmpty()) {
                val node = open.remove()
                if (node.position.equals(endPosition)) {
//                    println("End Goal Found | Cost = ${node.cost}")
                    if (shortestCost > node.cost) {
                        shortestCost = node.cost
                        shortestPosition = node.position
                    }
                    open.clear()
                    positionVisited.clear()
                    break
                }
                addVisitedPosition(positionVisited, node.position)
                expandNode(open, positionVisited, grid, endPosition, node)
            }
        }
//        println("Shortest Distance = ${shortestCost} at Position = ${shortestPosition}")
        return shortestCost
    }

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
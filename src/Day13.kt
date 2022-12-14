
fun main() {
    fun removeOuterSquareBrackets(line: String) : String {
        val sb = StringBuilder(line)
        val openSquareBracketIndex = sb.indexOfFirst { it.equals('[') }
        sb.replace(openSquareBracketIndex, openSquareBracketIndex + 1, "")
        val closeSquareBracketIndex = sb.indexOfLast { it.equals(']') }
        sb.replace(closeSquareBracketIndex, closeSquareBracketIndex + 1, "")
        return sb.toString()
    }

    fun itemizeList(line: String) : ArrayList<String> {
        val itemList = arrayListOf<String>()
        // This will skip square brackets in chunks
        var squareBracketCount = 0      // Increments when it sees '[' and decrements when it sees ']'
        val valueString = StringBuilder()
        val strippedLine = removeOuterSquareBrackets(line)
        strippedLine.forEach { ch ->
            if (ch.equals(',')) {
                if (squareBracketCount == 0) {
                    itemList.add(valueString.toString())
                    valueString.clear()
                } else {
                    valueString.append(ch)
                }
            } else if (ch.equals('[')) {
                squareBracketCount += 1
                valueString.append(ch)
            } else if (ch.equals(']')) {
                squareBracketCount -= 1
                valueString.append(ch)
            } else {
                valueString.append(ch)
            }
        }
        itemList.add(valueString.toString())
        return itemList
    }

    fun comparePackets(leftItems: ArrayList<String>, rightItems: ArrayList<String>) : Boolean? {
//        println("Comparing ${leftItems} Size: ${leftItems.size} to ${rightItems} Size: ${rightItems.size}")
        while (leftItems.isNotEmpty() || rightItems.isNotEmpty()) {
            val leftItem = if (leftItems.isNotEmpty()) leftItems.removeFirst() else ""
            val rightItem = if (rightItems.isNotEmpty()) rightItems.removeFirst() else ""
            if (leftItem == "") {
//                println("Left side smaller -> true")
                return true
            }
            if (rightItem == "") {
//                println("Right side smaller -> false")
                return false
            }
            if (leftItem.isNotEmpty() && leftItem[0] == '[' && rightItem.isNotEmpty() && rightItem[0] == '[') {
                val isStillValid = comparePackets(itemizeList(leftItem), itemizeList(rightItem))
                isStillValid?.let { return it }
            } else if (leftItem.isNotEmpty() && leftItem[0] == '[') {
                val isStillValid = comparePackets(itemizeList(leftItem), itemizeList('[' + rightItem + ']'))
                isStillValid?.let { return it }
            } else if (rightItem.isNotEmpty() && rightItem[0] == '[') {
                val isStillValid = comparePackets(itemizeList('[' + leftItem + ']'), itemizeList(rightItem))
                isStillValid?.let { return it }
            } else {
                // Start the comparison
                val leftItemValue = leftItem.toInt()
                val rightItemValue = rightItem.toInt()
//                println("Comparing ${leftItemValue} to ${rightItemValue}")
                if (leftItemValue < rightItemValue) {
//                    println("Left item smaller -> true")
                    return true
                } else if (leftItemValue > rightItemValue) {
//                    println("Right item smaller -> false")
                    return false
                }
            }
        }
        return null
    }

    fun part1(input: List<String>): Int {
        var lineCount = 0
        var pairIndex = 1
        var pairIndexTotal = 0
        var leftLine: String = ""
        var rightLine: String = ""
        input.forEach {
            if (lineCount == 0) {
                leftLine = it
            } else if (lineCount == 1) {
                rightLine = it
            }
            if (lineCount == 2) {                    // Should hit empty line
                val result = comparePackets(itemizeList(leftLine), itemizeList(rightLine))
                result?.let {
                    if (result) {
                        pairIndexTotal += pairIndex
                    }
                }
                leftLine = ""
                rightLine = ""
                lineCount = 0
                pairIndex += 1
            } else {
                lineCount += 1
            }
        }
        return pairIndexTotal
    }

    fun part2(input: List<String>): Int {
        // Remove empty lines
        val updatedInput = input.filter { it.isNotEmpty() }.toMutableList()
        updatedInput.add("[[2]]")
        updatedInput.add("[[6]]")
        updatedInput.sortWith(comparator = { a, b ->
            val isValid = comparePackets(itemizeList(a), itemizeList(b))
            if (isValid!!) 1 else -1
        })
        updatedInput.reverse()
        // Display the lines
//        updatedInput.forEach { println(it) }
        val dividerPacketIndex1 = (updatedInput.indexOfFirst { it == "[[2]]" } + 1)
        val dividerPacketIndex2 = (updatedInput.indexOfFirst { it == "[[6]]" } + 1)
        return dividerPacketIndex1 * dividerPacketIndex2
    }

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
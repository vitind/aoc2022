
fun main() {
    fun getSectionCount(sectionStart: Int, sectionEnd: Int) : Int {
        val range = sectionEnd - sectionStart
        return if (range == 1) { range } else { range + 1 }
    }

    fun getSectionBounds(sectionString: String) : List<Int> = sectionString.split("-").map { it.toInt() }

    fun part1(input: List<String>): Int {
        var pairSectionOverlapCount = 0
        input.forEach {
            val elfSections = it.split(",")
            val firstElfSections = getSectionBounds(elfSections[0])
            val secondElfSections = getSectionBounds(elfSections[1])
            if (getSectionCount(firstElfSections[0], firstElfSections[1]) <= getSectionCount(secondElfSections[0], secondElfSections[1])) {
                if ((firstElfSections[0] >= secondElfSections[0]) && (firstElfSections[1] <= secondElfSections[1])) {
                    pairSectionOverlapCount += 1
                }
            } else {        // firstElfSections >= secondElfSections
                if ((firstElfSections[0] <= secondElfSections[0]) && (firstElfSections[1] >= secondElfSections[1])) {
                    pairSectionOverlapCount += 1
                }
            }
        }
        return pairSectionOverlapCount
    }

    fun part2(input: List<String>): Int {
        var pairSectionOverlapCount = 0
        input.forEach {
            val elfSections = it.split(",")
            val firstElfSections = getSectionBounds(elfSections[0])
            val secondElfSections = getSectionBounds(elfSections[1])
            val firstElfSectionRangeSet = (firstElfSections[0]..firstElfSections[1]).toSet()
            val secondElfSectionRangeSet = (secondElfSections[0]..secondElfSections[1]).toSet()
            if (firstElfSectionRangeSet.intersect(secondElfSectionRangeSet).size > 0) {     // If there is any overlapping section IDs
                pairSectionOverlapCount += 1
            }
        }
        return pairSectionOverlapCount
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
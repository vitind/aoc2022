
fun main() {
    val START_PACKET_OFFSET = 4
    val MESSAGE_PACKET_OFFSET = 14

    fun getMarkerDataStreamPosition(dataStream: String, packetLength: Int) : Int {
        var currPos = 0
        var startPos = -1
        while (currPos + packetLength < dataStream.length) {
            val lastPos = currPos + packetLength
            val packet = dataStream.slice(currPos until lastPos)
            if (packet.toSet().size == packetLength) {
                startPos = lastPos
                break
            }
            currPos += 1
        }
        return startPos
    }

    fun part1(input: List<String>): Int {
        val dataStream = input[0]
        return getMarkerDataStreamPosition(dataStream, START_PACKET_OFFSET)
    }

    fun part2(input: List<String>): Int {
        val dataStream = input[0]
        return getMarkerDataStreamPosition(dataStream, MESSAGE_PACKET_OFFSET)
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
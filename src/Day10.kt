enum class InstructionType { NULL, NOOP, ADDX }

fun main() {
    data class Instruction(val instructionType: InstructionType, var instructionArg: Int = 0, var cycleElapsed: Int = 0)

    var regX = 1
    var cycle = 1
    var totalSignalStrength = 0

    fun resetVariables() {
        regX = 1
        cycle = 1
        totalSignalStrength = 0
    }

    val cycleDisplayUpdateSet = listOf(20, 60, 100, 140, 180, 220).toSet()
    fun displayRequiredUpdates() {
        if (cycleDisplayUpdateSet.contains(cycle)) {
            val signalStrength = cycle * regX
            totalSignalStrength += signalStrength
            println("Cycle = ${cycle} | Signal Strength = ${cycle} * ${regX} = ${signalStrength}")
        }
    }

    fun drawPixel() {
        val spritePixelPositionSet = listOf<Int>(regX, regX + 1, regX + 2).toSet()
        if (spritePixelPositionSet.contains(cycle % 40)) {
            print("#")
        } else {
            print(".")
        }
        if ((cycle % 40) == 0) {
            println()
        }
    }

    fun executeInstructionPart1(newInstruction: Instruction) {
        var cycleElapsed = 0
        when (newInstruction.instructionType) {
            InstructionType.ADDX -> {
                while (cycleElapsed < 2) {
                    displayRequiredUpdates()
                    cycleElapsed += 1
                    cycle += 1
                }
                regX += newInstruction.instructionArg
            }
            InstructionType.NOOP -> {
                while (cycleElapsed < 1) {
                    displayRequiredUpdates()
                    cycleElapsed += 1
                    cycle += 1
                }
            }
            else -> {
                // Do nothing
            }
        }
    }

    fun executeInstructionPart2(newInstruction: Instruction) {
        var cycleElapsed = 0
        when (newInstruction.instructionType) {
            InstructionType.ADDX -> {
                while (cycleElapsed < 2) {
                    drawPixel()
                    cycleElapsed += 1
                    cycle += 1
                }
                regX += newInstruction.instructionArg
            }
            InstructionType.NOOP -> {
                while (cycleElapsed < 1) {
                    drawPixel()
                    cycleElapsed += 1
                    cycle += 1
                }
            }
            else -> {
                // Do nothing
            }
        }
    }

    val INSTRUCTION_TYPE_INDEX = 0
    val INSTRUCTION_ARG0_INDEX = 1

    fun part1(input: List<String>): Int {
        resetVariables()
        input.forEach { line ->
            val instructionArgs = line.split(" ")
            val newInstruction = when (instructionArgs[INSTRUCTION_TYPE_INDEX]) {
                "noop" -> {
                    Instruction(InstructionType.NOOP)
                }
                "addx" -> {
                    val addAmount = instructionArgs[INSTRUCTION_ARG0_INDEX].toInt()
                    Instruction(InstructionType.ADDX, addAmount)
                }
                else -> {
                    Instruction(InstructionType.NULL)
                }
            }
            executeInstructionPart1(newInstruction)
        }
        return totalSignalStrength
    }

    fun part2(input: List<String>): Int {
        resetVariables()
        input.forEach { line ->
            val instructionArgs = line.split(" ")
            val newInstruction = when (instructionArgs[INSTRUCTION_TYPE_INDEX]) {
                "noop" -> {
                    Instruction(InstructionType.NOOP)
                }
                "addx" -> {
                    val addAmount = instructionArgs[INSTRUCTION_ARG0_INDEX].toInt()
                    Instruction(InstructionType.ADDX, addAmount)
                }
                else -> {
                    Instruction(InstructionType.NULL)
                }
            }
            executeInstructionPart2(newInstruction)
        }
        return 0
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input)
}
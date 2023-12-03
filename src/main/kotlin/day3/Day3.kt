package day3

import java.io.File

fun main() {
    val day3 = Day3("ChallengeResources/day3_puzzle_input.txt")
    day3.solvePart1()
    day3.solvePart2()
}

class Day3(filename: String) {
    private val numbers = mutableListOf<Number>()
    private val gears = mutableListOf<Gear>()

    init {
        parse(File(filename).readLines())
    }

    private fun parse(lines: List<String>) {
        var prePreviousNumbers = mutableMapOf<Int, String>()
        var prePreviousIndexedSymbols = mutableMapOf<Int, Char>()
        var previousNumbers = mutableMapOf<Int, String>()
        var previousIndexedSymbols = mutableMapOf<Int, Char>()
        lines.forEach { line ->
            val indexedNumbers = mutableMapOf<Int, String>()
            val indexedSymbols = mutableMapOf<Int, Char>()
            var i = 0
            while (i < line.length) {
                if (line[i] != '.') {
                    if (!(line[i].isDigit())) {
                        indexedSymbols[i] = line[i++]
                        continue
                    }
                    val index = i
                    var numberString = ""
                    while (line[i].isDigit()) {
                        numberString += line[i]
                        if (++i == line.length) break
                    }
                    indexedNumbers[index] = numberString
                } else i++
            }

            previousNumbers.forEach {
                val leftIndex = it.key - 1
                val rightIndex = it.key + it.value.length
                val isPartNumber =
                    prePreviousIndexedSymbols.any { it.key in leftIndex..rightIndex } ||
                            previousIndexedSymbols.any { it.key == leftIndex || it.key == rightIndex } ||
                            indexedSymbols.any { it.key in leftIndex..rightIndex }

                numbers.add(Number(it.value.toInt(), isPartNumber))
            }
            previousIndexedSymbols.filter { it.value == '*' }
                .forEach {
                    val leftIndex = it.key - 1
                    val rightIndex = it.key + 1
                    val adjacentNumbers =
                        prePreviousNumbers.filter { it.key in leftIndex..rightIndex || it.key + it.value.length - 1 in leftIndex..rightIndex }.map { it.value.toInt() } +
                                previousNumbers.filter { it.key == rightIndex || it.key + it.value.length - 1 == leftIndex }.map { it.value.toInt() } +
                                indexedNumbers.filter { it.key in leftIndex..rightIndex || it.key + it.value.length - 1 in leftIndex..rightIndex }.map { it.value.toInt() }
                    if (adjacentNumbers.size == 2) gears.add(Gear(adjacentNumbers[0],adjacentNumbers[1]))
                }

            prePreviousIndexedSymbols = previousIndexedSymbols
            previousIndexedSymbols = indexedSymbols
            prePreviousNumbers = previousNumbers
            previousNumbers = indexedNumbers
        }

        previousNumbers.forEach {
            val leftIndex = it.key - 1
            val rightIndex = it.key + it.value.length
            val isPartNumber =
                prePreviousIndexedSymbols.any { it.key in leftIndex..rightIndex } ||
                        previousIndexedSymbols.any { it.key == leftIndex || it.key == rightIndex }
            numbers.add(Number(it.value.toInt(), isPartNumber))
        }
        previousIndexedSymbols.filter { it.value == '*' }
            .forEach {
                val leftIndex = it.key - 1
                val rightIndex = it.key + 1
                val adjacentNumbers =
                    prePreviousNumbers.filter { it.key in leftIndex..rightIndex || it.key + it.value.length -1 in leftIndex..rightIndex }.map { it.value.toInt() } +
                            previousNumbers.filter { it.key == rightIndex || it.key + it.value.length - 1 == leftIndex }.map { it.value.toInt() }
                if (adjacentNumbers.size == 2) gears.add(Gear(adjacentNumbers[0],adjacentNumbers[1]))
            }
    }

    fun solvePart1() =
        println(numbers
            .filter { it.isPartNumber }
            .sumOf { it.value })

    fun solvePart2() =
        println(gears.sumOf { it.ratio() })

    private fun Gear.ratio() = this.partNumber1 * this.partNumber2
}


data class Number(val value: Int, val isPartNumber: Boolean = false)

data class Gear(val partNumber1: Int, val partNumber2: Int)


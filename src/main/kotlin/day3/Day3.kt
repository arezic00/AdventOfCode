package day3

import java.io.File
import java.nio.charset.Charset

fun main() {
    Day3().solvePart1("ChallengeResources/day3_puzzle_input.txt")
}

class Day3 {
    private fun parse(lines: List<String>): MutableList<Number> {
        val numbers = mutableListOf<Number>()
        var prePreviousSymbolIndexes = mutableListOf<Int>()
        var previousNumbers = mutableMapOf<Int, String>()
        var previousSymbolIndexes = mutableListOf<Int>()
        lines.forEach { line ->
            val indexedNumbers = mutableMapOf<Int, String>()
            val symbolIndexes = mutableListOf<Int>()
            var i = 0
            while (i < line.length) {
                if (line[i] != '.') {
                    if (!(line[i].isDigit())) {
                        symbolIndexes.add(i++)
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
                    prePreviousSymbolIndexes.any { it in leftIndex..rightIndex } ||
                    previousSymbolIndexes.any { it == leftIndex || it == rightIndex } ||
                    symbolIndexes.any { it in leftIndex..rightIndex }
                numbers.add(Number(it.value.toInt(),isPartNumber))
            }
            prePreviousSymbolIndexes = previousSymbolIndexes
            previousSymbolIndexes = symbolIndexes
            previousNumbers = indexedNumbers
        }
        previousNumbers.forEach {
            val leftIndex = it.key - 1
            val rightIndex = it.key + it.value.length
            val isPartNumber =
                prePreviousSymbolIndexes.any { it in leftIndex..rightIndex } ||
                previousSymbolIndexes.any { it == leftIndex || it == rightIndex }
            numbers.add(Number(it.value.toInt(),isPartNumber))
        }
        return numbers
    }

    fun solvePart1(filename: String) =
        println(parse(File(filename).readLines(Charset.defaultCharset()))
            .filter { it.isPartNumber }
            .sumOf { it.value })
}


data class Number(val value: Int, val isPartNumber: Boolean = false)


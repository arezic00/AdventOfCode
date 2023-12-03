package day3

import java.io.File
import java.nio.charset.Charset

fun main() {
    Day3().solvePart1("ChallengeResources/day3_puzzle_input.txt")
}

class Day3 {
    private fun parse(lines: List<String>): MutableList<Number> {
        val numbers = mutableListOf<Number>()
        var prePreviousIndexedSymbols = mutableMapOf<Int,Char>()
        var previousNumbers = mutableMapOf<Int, String>()
        var previousIndexedSymbols = mutableMapOf<Int,Char>()
        lines.forEach { line ->
            val indexedNumbers = mutableMapOf<Int, String>()
            val indexedSymbols = mutableMapOf<Int,Char>()
            var i = 0
            while (i < line.length) {
                if (line[i] != '.') {
                    if (!(line[i].isDigit())) {
                        indexedSymbols[i++] = line[i]
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
                numbers.add(Number(it.value.toInt(),isPartNumber))
            }
            prePreviousIndexedSymbols = previousIndexedSymbols
            previousIndexedSymbols = indexedSymbols
            previousNumbers = indexedNumbers
        }
        previousNumbers.forEach {
            val leftIndex = it.key - 1
            val rightIndex = it.key + it.value.length
            val isPartNumber =
                prePreviousIndexedSymbols.any { it.key in leftIndex..rightIndex } ||
                previousIndexedSymbols.any { it.key == leftIndex || it.key == rightIndex }
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


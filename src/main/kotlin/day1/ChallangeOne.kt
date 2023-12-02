package day1

import java.io.File
import java.nio.charset.Charset

fun main() {
    val dayOne = DayOne()
    println(dayOne.solvePartOne("ChallengeResources/puzzle_input.txt"))
}

class DayOne {

    private fun readFileLines(filename: String) = File(filename).readLines(Charset.defaultCharset())

    private fun lineToCalibrationValue(line: String) : Int {
        val firstDigit = firstDigit(line)
        return if (firstDigit == -1) 0 else firstDigit*10 + lastDigit(line)
    }

    private fun firstDigit(line: String) : Int {
        line.forEach { if(it.isDigit()) return it.digitToInt() }
        return -1
    }

    private fun lastDigit(line: String) : Int {
        line.reversed().forEach { if(it.isDigit()) return it.digitToInt() }
        return -1
    }

    private fun sumCalibrationValues(lines: List<String>) : Int {
        var sum = 0
        lines.forEach { sum += lineToCalibrationValue(it) }
        return sum
    }

    fun solvePartOne(filename: String) : Int {
        return sumCalibrationValues(readFileLines(filename))
    }
}
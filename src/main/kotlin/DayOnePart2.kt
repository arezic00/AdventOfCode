import java.io.File
import java.nio.charset.Charset

fun main() {
    val dayOne = DayOnePart2()
    println(dayOne.solvePartTwo("ChallengeResources/puzzle_input.txt"))
}

class DayOnePart2 {
    private val words = listOf("zero","one","two","three","four","five","six","seven","eight","nine")

    private fun firstDigitWithIndex(line: String) : Pair<Int,Int> {
        line.forEachIndexed { index, c ->
            if (c.isDigit()) return Pair(c.digitToInt(),index)
        }
        return Pair(-1,-1)
    }

    private fun lastDigitWithIndex(line: String) : Pair<Int,Int> {
        line.reversed().forEachIndexed { index, c ->
            if (c.isDigit()) return Pair(c.digitToInt(),index)
        }
        return Pair(-1,-1)
    }

    private fun firstWordWithIndex(line: String) : Pair<String,Int> {
        var firstWordIndex = 9999
        var firstWord = ""
        words.forEach {
            val index = line.indexOf(it)
            if (index != -1 && index < firstWordIndex) {
                firstWordIndex = index
                firstWord = it
            }
        }
        return Pair(firstWord,firstWordIndex)
    }

    private fun lastWordWithIndex(line: String) : Pair<String,Int> {
        var firstWordIndex = 9999
        var firstWord = ""
        words.forEach {
            val index = line.reversed().indexOf(it.reversed())
            if (index != -1 && index < firstWordIndex) {
                firstWordIndex = index
                firstWord = it
            }
        }
        return Pair(firstWord,firstWordIndex)
    }

    private fun lineToCalibrationValue(line: String) : Int {
        val firstWordPair = firstWordWithIndex(line)
        val firstDigitPair = firstDigitWithIndex(line)

        if (firstDigitPair.second == -1 && firstWordPair.second == 9999)
            return 0

        if (firstDigitPair.second == -1) {
            return words.indexOf(firstWordPair.first) * 10 + words.indexOf(lastWordWithIndex(line).first)
        }
        else if (firstWordPair.second == 9999) {
            return firstDigitPair.first * 10 + lastDigitWithIndex(line).first
        }

        val firstDigit : Int = if (firstDigitPair.second < firstWordPair.second) firstDigitPair.first else words.indexOf(firstWordPair.first)
        val lastWordPair = lastWordWithIndex(line)
        val lastDigitPair = lastDigitWithIndex(line)
        val lastDigit : Int = if (lastDigitPair.second < lastWordPair.second) lastDigitPair.first else words.indexOf(lastWordPair.first)
        return firstDigit * 10 + lastDigit
    }

    private fun sumCalibrationValues(lines: List<String>) : Int {
        var sum = 0
        lines.forEach { sum += lineToCalibrationValue(it) }
        return sum
    }

    private fun readFileLines(filename: String) = File(filename).readLines(Charset.defaultCharset())

    fun solvePartTwo(filename: String) :Int {
        return sumCalibrationValues(readFileLines(filename))
    }
}
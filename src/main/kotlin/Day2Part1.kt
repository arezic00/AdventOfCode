import java.io.File
import java.nio.charset.Charset

const val RED_MAX = 12
const val GREEN_MAX = 13
const val BLUE_MAX = 14
fun main() {
    val day2 = Day2Part1()
    println(day2.solvePart1("ChallengeResources/day2_puzzle_input.txt"))
    println(day2.solvePart2("ChallengeResources/day2_puzzle_input.txt"))
}

class Day2Part1 {

    private fun readFileLines(filename: String) = File(filename).readLines(Charset.defaultCharset())

    private fun firstNumberWithEndIndex(string: String) : Pair<Int,Int>{
        var number = ""
        var currentIndex = 0
        for (index in string.indices) {
            if (string[index].isDigit()) number += string[index]
            else {
                currentIndex = index
                break
            }
        }
        return Pair(number.toInt(),currentIndex)
    }
    private fun lineToGame(line: String): Game {
        var substring = line.substring(5)
        val idWithIndex = firstNumberWithEndIndex(substring)
        substring = substring.substring(idWithIndex.second + 2)

        val handfuls = mutableListOf<HandfulOfCubes>()
        var handful = HandfulOfCubes()

        while(true) {
            val numOfCubesWithEndIndex = firstNumberWithEndIndex(substring)
            substring = substring.substring(numOfCubesWithEndIndex.second + 1)
            when (substring[0]) {
                'r' -> {
                    handful.red = numOfCubesWithEndIndex.first
                    substring = substring.substring(2)
                }
                'g' -> {
                    handful.green = numOfCubesWithEndIndex.first
                    substring = substring.substring(4)
                }
                'b' -> {
                    handful.blue = numOfCubesWithEndIndex.first
                    substring = substring.substring(3)
                }
            }
            if (substring.length == 1) {
                handfuls.add(handful)
                break
            }

            if (substring[1] ==';') {
                handfuls.add(handful)
                handful = HandfulOfCubes()
            }

            substring = substring.substring(3)
        }
        return Game(idWithIndex.first,handfuls)

    }

    private fun linesToGames(lines: List<String>) : List<Game> {
        val result = mutableListOf<Game>()
        lines.forEach { result.add(lineToGame(it)) }
        return result
    }

    private fun isGamePossible(game: Game): Boolean {
        game.handfuls.forEach {
            if (it.red > RED_MAX || it.green > GREEN_MAX || it.blue > BLUE_MAX)
                return false
        }
        return true
    }

    private fun sumPossibleGameIDs(games: List<Game>) : Int {
        var sum = 0
        games.forEach { if (isGamePossible(it)) sum += it.id }
        return sum
    }

    fun solvePart1(filename: String) : Int {
        return sumPossibleGameIDs(linesToGames(readFileLines(filename)))
    }

    fun solvePart2(filename: String) : Int {
        return sumPowerOfSets(linesToGames(readFileLines(filename)))
    }

    private fun maxBlueCubes(game: Game) : Int {
        var max = 0
        game.handfuls.forEach { if (it.blue > max) max = it.blue }
        return max
    }

    private fun maxRedCubes(game: Game) : Int {
        var max = 0
        game.handfuls.forEach { if (it.red > max) max = it.red }
        return max
    }

    private fun maxGreenCubes(game: Game) : Int {
        var max = 0
        game.handfuls.forEach { if (it.green > max) max = it.green }
        return max
    }

    private fun powerOfSet(game: Game) : Int {
        return maxBlueCubes(game) * maxGreenCubes(game) * maxRedCubes(game)
    }

    private fun sumPowerOfSets(games: List<Game>) : Int {
        return games.sumOf { powerOfSet(it) }
    }


}

data class Game(val id: Int, val handfuls: List<HandfulOfCubes>)

data class HandfulOfCubes(var red: Int = 0, var green: Int = 0, var blue: Int = 0)
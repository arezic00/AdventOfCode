const val RED_MAX = 12
const val GREEN_MAX = 13
const val BLUE_MAX = 14
fun main() {}

class Day2Part1 {

    val games: List<Game> = listOf(
        Game(1, listOf(
            HandfulOfCubes(3,4),
            HandfulOfCubes(1,2,6),
            HandfulOfCubes(green = 2))))

    private fun isGamePossible(game: Game): Boolean {
        game.handfuls.forEach {
            if (it.red > RED_MAX || it.green > GREEN_MAX || it.blue > BLUE_MAX)
                return false
        }
        return true
    }
}

data class Game(val id: Int, val handfuls: List<HandfulOfCubes>)

data class HandfulOfCubes(val red: Int = 0, val green: Int = 0, val blue: Int = 0)
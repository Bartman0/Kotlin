package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    var rightPosition = 0
    var wrongPosition = 0
    var wrong: List<Char> = emptyList()
    var remaining: List<Char> = emptyList()
    guess.withIndex().forEach {
        (index, c) -> if (c == secret[index]) {
            rightPosition++
        } else {
            wrong = wrong + secret[index]
            remaining += c
        }
    }

    wrong.forEach { c -> if (c in remaining) {
        wrongPosition++
        remaining -= c
    } }
    return Evaluation(rightPosition, wrongPosition)
}

/*
data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {

    val rightPositions = secret.zip(guess).count { (s, g) -> s == g }

    val commonLetters = "ABCDEF".sumBy { ch ->

        Math.min(secret.count { s -> ch == s }, guess.count { g -> ch == g })
    }
    return Evaluation(rightPositions, commonLetters - rightPositions)
}

fun main(args: Array<String>) {
    val result = Evaluation(rightPosition = 1, wrongPosition = 1)
    evaluateGuess("BCDF", "ACEB") eq result
    evaluateGuess("AAAF", "ABCA") eq result
    evaluateGuess("ABCA", "AAAF") eq result
}
 */
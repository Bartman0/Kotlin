package rationals

import java.math.BigInteger
import kotlin.math.sign

class Rational(var n: BigInteger, var d: BigInteger) : Comparable<Rational> {
    init {
        val gcd = n.gcd(d)
        n = n.div(gcd)
        d = d.div(gcd)
        if (d < 0.toBigInteger()) {
            n = -n
            d = -d
        }
    }

    operator fun unaryMinus(): Rational = Rational(n.negate(), d)

    operator fun plus(r2: Rational) = Rational(n.times(r2.d) + r2.n.times(d), d.times(r2.d))

    operator fun minus(r2: Rational) = plus(-r2)

    operator fun times(r2: Rational) = Rational(n.times(r2.n), d.times(r2.d))

    operator fun div(r2: Rational) = Rational(n.times(r2.d), d.times(r2.n))

    override fun compareTo(other: Rational): Int = n.times(other.d).compareTo(other.n.times(d))

    override fun equals(other: Any?): Boolean {
        other as Rational
        return compareTo(other) == 0
    }

    override fun toString(): String {
        return when {
            d == 1.toBigInteger() -> "$n"
            else -> "$n/$d"
        }
    }
}

infix fun Int.divBy(r2: Int) : Rational = Rational(toBigInteger(), r2.toBigInteger())

infix fun Long.divBy(r2: Long) : Rational = Rational(toBigInteger(), r2.toBigInteger())

infix fun BigInteger.divBy(other: BigInteger) : Rational = Rational(this, other)

fun String.toRational(): Rational {
    val number = split("/")

    return when {
        number.size == 1 -> Rational(number[0].toBigInteger(), 1.toBigInteger())
        else -> Rational(number[0].toBigInteger(), number[1].toBigInteger())
    }
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}
package nicestring

fun String.isNice(): Boolean {
    var condList = emptyList<Boolean>()
    condList += !Regex("b[aeu]").containsMatchIn(this)
    condList += 3 <= this.filter { it in "aeiou" }.count()
    condList +=  1 <= this.zipWithNext() { a, b -> a == b }.filter { it == true }.count()
    return condList.filter { it == true }.count() >= 2
}
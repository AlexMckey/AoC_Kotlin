package math

fun Int.wrapAround(d: Int): Int = (this % d + d) % d

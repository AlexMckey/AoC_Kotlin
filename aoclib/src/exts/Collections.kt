package exts

fun <T> Iterable<T>.groupCount(): Map<T, Int> = this.groupingBy { it }.eachCount()
fun <T, B> Iterable<T>.groupCount(f: (T) -> B): Map<B, Int> = this.groupingBy { f(it) }.eachCount()

fun <T> Iterable<T>.headAndTail(): Pair<T, List<T>> = this.first() to this.drop(1)

val <T> Iterable<T>.tail: List<T>
    get() = drop(1)
val <T> Iterable<T>.head: T
    get() = first()
val <T> Iterable<T>.first: T
    get() = first()
val <T> Iterable<T>.second: T
    get() = drop(1).first()

inline fun <reified T>Array<Array<T>>.transpose(): Array<Array<T>> {
    return Array(this[0].size) { i -> Array(this.size) { j -> this[j][i] } }
}
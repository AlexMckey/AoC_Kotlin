package year2024.day09

import SomeDay
import year2024.DayOf2024

data class FS(val start: Int, val len: Int, val id: Int){
    val checkSum: Long get() =
        if (id < 0) 0L
        else (start..<(start+len)).sumOf { it.toLong() * id }
}

fun dropFS(fs: List<FS>, drops: List<FS>, dropLastFree: Boolean = true): List<FS> {
    val clear = fs.filterNot { it in drops }
    return if (dropLastFree)
        clear.dropLastWhile { it.id < 0 }
    else clear
}

fun dense(disk: List<FS>): List<FS> {
    tailrec fun rec(ds: List<FS>, acc: List<FS>): List<FS> {
        val fs = ds.firstOrNull { it.id < 0 }
        val fl = ds.last { it.id >= 0 }
        return when {
            fs == null -> acc + ds
            fs.len == fl.len ->
                rec(dropFS(ds, listOf(fs, fl)),acc + FS(fs.start, fl.len, fl.id))
            fs.len > fl.len ->
                rec(listOf(FS(fs.start + fl.len, fs.len - fl.len, -1))
                        + dropFS(ds, listOf(fs, fl)),
                    acc + FS(fs.start, fl.len, fl.id))
            else ->
                rec(dropFS(ds, listOf(fs, fl), false)
                        + FS(fl.start, fl.len - fs.len, fl.id),
                    acc + FS(fs.start, fs.len, fl.id))
        }
    }
    return rec(disk.dropLast(1), emptyList())
}

fun sparse(disk: List<FS>): List<FS> {
    tailrec fun rec(ds: List<FS>, acc: List<FS>): List<FS> {
        val fs = ds.firstOrNull { it.id < 0 }
        return if (fs == null) acc + ds
        else {
            val fl = ds.lastOrNull { it.id >= 0 && it.len <= fs.len && it.start > fs.start}
            when {
                fl == null ->
                    rec(dropFS(ds, listOf(fs)), acc)
                fl.len == fs.len ->
                    rec(dropFS(ds, listOf(fs, fl)),acc + FS(fs.start, fl.len, fl.id))
                else ->
                    rec(listOf(FS(fs.start + fl.len, fs.len - fl.len, -1))
                            + dropFS(ds, listOf(fs, fl)),
                        acc + FS(fs.start, fl.len, fl.id))
            }
        }
    }
    return rec(disk, emptyList())
}

object Day09: DayOf2024(9, "Disk Fragmenter") {

    private val disk = data
        .map { it.digitToInt() }
        .windowed(2,2,true)
        .withIndex()
        .fold(emptyList<FS>() to 0){ accPair, idxPair ->
            val (acc, pos) = accPair
            val (idx, value) = idxPair
            val fileLen = value.first()
            val freeLen = value.getOrElse(1){0}
            val file = FS(pos, fileLen, idx)
            val free = FS(pos + fileLen, freeLen, -1)
            acc + file + free to pos + fileLen + freeLen
        }.first

    override fun first(): Any? =
        dense(disk).sumOf { it.checkSum }

    override fun second(): Any? =
        sparse(disk).sumOf { it.checkSum }
}

fun main() = SomeDay.mainify(Day09)

//Year 2024, Day 9 : Disk Fragmenter
//  Part 1: 6448989155953
//    Time: 1.119s
//  Part 2: 6476642796832
//    Time: 717ms
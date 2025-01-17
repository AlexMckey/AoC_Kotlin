package year2024.day09

import exts.head
import exts.tail

val s = "2333133121414131402"

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

val fs = s
    .map { it.digitToInt() }
    .windowed(2,2,true)
    .withIndex()
    .fold(emptyList<FS>() to 0){ accp, idxp ->
        val (acc, pos) = accp
        val (idx, value) = idxp
        val flen = value.first()
        val slen = value.getOrElse(1){0}
        val file = FS(pos, flen, idx)
        val free = FS(pos + flen, slen, -1)
        acc + file + free to pos + flen + slen
    }.first
fs

fun fsToDisk(fs: List<FS>): List<Int?> {
    fun rec(n: FS, ns: List<FS>, acc: List<Int?>): List<Int?> =
        when {
            ns.isEmpty() -> acc
            n.id >= 0 -> rec(ns.head, ns.tail, acc + List(n.len) { n.id })
            n.id < 0 -> rec(ns.head, ns.tail, acc + List(n.len) { null })
            else -> acc
        }
    return rec(fs.head, fs.tail, emptyList())
}

val disk = fsToDisk(fs)
disk

val neb = disk.filterNotNull().reversed()
neb
val eb = disk.indices.filter { disk[it] == null && it < neb.size }
eb
neb.size
val r1 = eb.zip(neb) + disk.take(neb.size).indices.filter { disk[it] != null }.map { it to disk[it] }
r1

fun dense(fs: List<FS>): List<Pair<Int,Int?>> {
    val ds = fsToDisk(fs)
    val files = ds.filterNotNull().reversed()
    val emptys = ds.indices.filter { idx -> ds[idx] == null && idx < files.size }
    val zipped = emptys.zip(files)
    val other = ds.take(files.size).withIndex().filter { (_, v) -> v != null }.map { (idx, v) -> idx to v}
    return zipped + other
}

fun dense_alt(disk: List<FS>, acc: List<FS>): List<FS> {
    val fs = disk.firstOrNull{ it.id < 0 }
    val fl = disk.last { it.id >= 0 }
    return when {
        fs == null -> acc + disk
        fs.len == fl.len -> dense_alt(disk.filterNot { it == fs || it == fl}.dropLastWhile { it.id < 0 }, acc + FS(fs.start, fl.len, fl.id))
        fs.len > fl.len -> dense_alt(listOf(FS(fs.start + fl.len, fs.len - fl.len, -1)) + disk.filterNot { it == fl || it == fs }.dropLastWhile { it.id < 0 }, acc + FS(fs.start, fl.len, fl.id))
        else -> dense_alt(disk.filterNot { it == fs || it == fl } + FS(fl.start, fl.len - fs.len, fl.id), acc + FS(fs.start, fs.len, fl.id))
    }
}

val r3 = dense_alt(fs, emptyList())
r3
r3.sumOf { it.checkSum }

fun checkSumAlt(ar: List<Pair<Int,Int?>>): Long =
    ar.sumOf { (idx, b) -> idx * (b?.toLong() ?: 0)  }
checkSumAlt(r1)
checkSumAlt(dense(fs))

fun deflate(acc: Array<Int?>, il: Int, ir: Int): Array<Int?> {
    return when {
        il > ir -> acc.take(il).filterNotNull().toTypedArray()
        acc[ir] == null -> deflate(acc, il, ir - 1)
        acc[il] != null -> deflate(acc, il + 1, ir)
        else -> {
            acc[il] = acc[ir]
            deflate(acc, il + 1, ir - 1)
        }
    }
}
val res = deflate(disk.toTypedArray(),0,disk.size-1)
res.toList()
fun checkSum(ar: Array<Int?>): Long =
    ar.withIndex().sumOf { (idx, b) -> idx * (b?.toLong() ?: 0) }
checkSum(res)

val (frees, files) = fs.partition { it.id < 0 }
frees
files.reversed()


val frs = fs.first { it.id < 0 }
frs
val fls = fs.lastOrNull { it.id >= 0 && it.len <= frs.len }
fls
fs.filterNot { it == frs || it == fls }

fun sparse(disk: List<FS>): List<FS> {
    tailrec fun rec(ds: List<FS>, acc: List<FS>): List<FS> {
        val fs = ds.firstOrNull { it.id < 0 }
        return if (fs == null) acc + ds
        else {
            val fl = ds.lastOrNull { it.id >= 0 && it.len <= fs.len }
            when {
                fl == null ->
                    rec(dropFS(ds, listOf(fs)), acc)
                fl.len == fs.len ->
                    rec(dropFS(ds, listOf(fs, fl)),acc + FS(fs.start, fl.len, fl.id))
                else ->
                    rec(listOf(FS(fs.start + fl.len, fs.len - fl.len, -1))
                            + dropFS(ds, listOf(fs, fl)),
                        acc + FS(fs.start, fl.len, fl.id)
                    )
            }
        }
    }
    return rec(disk, emptyList())
}

val r2 = sparse(fs)
r2
r2.sumOf { it.checkSum }

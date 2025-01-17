package year2024.day04

import grid.Grid
import pos.Dirs

val s = "MMMSXXMASM\n" +
        "MSAMXMSMSA\n" +
        "AMXSXMAAMM\n" +
        "MSAMASMSMX\n" +
        "XMASAMXAMM\n" +
        "XXAMMXXAMA\n" +
        "SMSMSASXSS\n" +
        "SAXAMASAAA\n" +
        "MAMMMXMMMM\n" +
        "MXMXAXMASX"
val ls = s.split("\n")
val mg = Grid(ls){it}
val psx = mg.filter { ch: Char -> ch == 'X' }.allPos
psx
val x1 = psx.first()
val s4 = Dirs.All.map { d -> (0..<4).map { mg.getOrElse(x1 + d * it, ' ') }.joinToString("") }
s4
psx.sumOf { p -> Dirs.All.count { d -> (0..<4).map { mg.getOrElse(p + d * it, ' ') }.joinToString("") == "XMAS" } }
val psa = mg.filter { ch: Char -> ch == 'A' }.allPos
psa
val a1 = psa.first()
Dirs.Diag.map { d -> mg.getOrElse(a1 + d, ' ') }.sorted().joinToString("")
val pat = "MMSS"
psa.count { p -> Dirs.Diag.map { d -> mg.getOrElse(p + d, ' ') }.sorted().joinToString("") == pat }
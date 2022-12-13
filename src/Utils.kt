import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads content as a whole from the given input txt file
 */
fun read(name: String) = File("src", "$name.txt")
    .readText()

/**
 * Reads lines from the given input txt file.
 */
fun readLines(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

typealias Grid<T> = Map<Point, T>
data class Point(val x: Int, val y: Int)
val Point.neighbours: List<Point>
    get() = arrayOf((-1 to 0), (1 to 0), (0 to -1), (0 to 1)).map { (dx, dy) -> Point(x + dx, y + dy) }
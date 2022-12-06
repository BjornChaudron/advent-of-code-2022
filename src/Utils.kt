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

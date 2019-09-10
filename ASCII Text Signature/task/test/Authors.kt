import java.lang.IllegalStateException
import java.util.*

class Letter(
        val rows: List<String>
) {

    init {
        if (rows.size != 3) {
            throw IllegalArgumentException("Letter $rows must have 3 rows.")
        }
        if (rows.distinctBy { it.length }.size != 1) {
            throw IllegalStateException("Bad letter. Not equal width in lines: ${rows.distinctBy { it.length }}")
        }
    }

    override fun toString() = rows.joinToString("\n")

    val width get() = rows[0]
}

val font = """
____ ___  ____ ___  ____ ____ ____ _  _ _  _ _  _ _    _  _ _  _ ____ ___  ____ ____ ____ ___ _  _ _  _ _ _ _ _  _ _   _ ___+
|__| |__] |    |  \ |___ |___ | __ |__| |  | |_/  |    |\/| |\ | |  | |__] |  | |__/ [__   |  |  | |  | | | |  \/   \_/    /+
|  | |__] |___ |__/ |___ |    |__] |  | | _| | \_ |___ |  | | \| |__| |    |_\| |  \ ___]  |  |__|  \/  |_|_| _/\_   |    /__
""".trim('\n').replace('+', ' ') // this works so, because someone (like stepik) possibly deletes trailing spaces in lines.


// type your solution here

fun makeLetters(): Map<Char, Letter> {
    val lines = font.split('\n')

    val letterBuilders = List(3) { StringBuilder() }
    val letters = mutableListOf<Letter>()

    for (i in 0 until lines[0].length) {
        val slice = lines.map { it[i] }

        if (slice.all { it == ' ' }) {
            letters += Letter(letterBuilders.map { it.toString() })
            letterBuilders.forEach { it.clear() }
        } else {
            letterBuilders.zip(slice).forEach { (b, s) -> b.append(s) }
        }
    }
    // Don't forget about the last!
    letters += Letter(letterBuilders.map { it.toString() })

    val charsToLetters = letters.mapIndexed { i, letter -> 'a' + i to letter }.toMap().toMutableMap()
    charsToLetters[' '] = Letter(List(3) { "    " })
    return charsToLetters
}

/** Wrap with asterisks. */
fun framed(lines: List<String>): String {

    val builder = StringBuilder()
    builder.append("*".repeat(lines[0].length + 6) + "\n")
    lines.forEach { line -> builder.append("*  $line  *\n") }
    builder.append("*".repeat(lines[0].length + 6))
    return builder.toString()
}

fun centeredLines(lines: List<String>): List<String> {
    val maxLen = lines.map { it.length }.max()!!

    return lines.map { line ->
        val need = maxLen - line.length
        " ".repeat(need / 2) + line + " ".repeat((need + 1) / 2)
    }
}

fun authors(input: String): String {
    val scanner = Scanner(input)
    val name = scanner.next() + " " + scanner.next()
    scanner.nextLine()
    val status = scanner.nextLine()

    val letters = makeLetters()
    val nameLetters = name.toLowerCase().map { letters[it]!! }

    val lines = (0..2).map { i ->
        nameLetters.map { it.rows[i] }.joinToString(" ")
    } + status

    return framed(centeredLines(lines))
}

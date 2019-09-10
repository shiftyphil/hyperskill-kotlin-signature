package signature

import java.lang.Integer.max
import java.util.Scanner

enum class Font(val character: Char, val data: Array<String>) {
    ERR('*', arrayOf(
            "****",
            "****",
            "****"
    )),
    SPACE(' ', arrayOf(
            "    ",
            "    ",
            "    "
    )),
    A('a', arrayOf(
            "____",
            "|__|",
            "|  |"
    )),
    B('b', arrayOf(
            "___ ",
            "|__]",
            "|__]"
    )),
    C('c', arrayOf(
            "____",
            "|   ",
            "|___"
    )),
    D('d', arrayOf(
            "___ ",
            "|  \\",
            "|__/"
    )),
    E('e', arrayOf(
            "____",
            "|___",
            "|___"
    )),
    F('f', arrayOf(
            "____",
            "|___",
            "|   "
    )),
    G('g', arrayOf(
            "____",
            "| __",
            "|__]"
    )),
    H('h', arrayOf(
            "_  _",
            "|__|",
            "|  |"
    )),
    I('i', arrayOf(
            "_",
            "|",
            "|"
    )),
    J('j', arrayOf(
            " _",
            " |",
            "_|"
    )),
    K('k', arrayOf(
            "_  _",
            "|_/ ",
            "| \\_"
    )),
    L('l', arrayOf(
            "_   ",
            "|   ",
            "|___"
    )),
    M('m', arrayOf(
            "_  _",
            "|\\/|",
            "|  |"
    )),
    N('n', arrayOf(
            "_  _",
            "|\\ |",
            "| \\|"
    )),
    O('o', arrayOf(
            "____",
            "|  |",
            "|__|"
    )),
    P('p', arrayOf(
            "___ ",
            "|__]",
            "|   "
    )),
    Q('q', arrayOf(
            "____",
            "|  |",
            "|_\\|"
    )),
    R('r', arrayOf(
            "____",
            "|__/",
            "|  \\"
    )),
    S('s', arrayOf(
            "____",
            "[__ ",
            "___]"
    )),
    T('t', arrayOf(
            "___",
            " | ",
            " | "
    )),
    U('u', arrayOf(
            "_  _",
            "|  |",
            "|__|"
    )),
    V('v', arrayOf(
            "_  _",
            "|  |",
            " \\/ "
    )),
    W('w', arrayOf(
            "_ _ _",
            "| | |",
            "|_|_|"
    )),
    X('x', arrayOf(
            "_  _",
            " \\/ ",
            "_/\\_"
    )),
    Y('y', arrayOf(
            "_   _",
            " \\_/ ",
            "  |  "
    )),
    Z('z', arrayOf(
            "___ ",
            "  / ",
            " /__"
    ))
    ;

    fun getWidth(): Int {
        return data[0].length
    }

    fun getRow(row: Int): String {
        return data[row]
    }

    companion object {
        fun getCharacter(c: Char): Font {
            return values().firstOrNull { it.character == c.toLowerCase() } ?: ERR
        }
    }
}

fun stars(length: Int): String {
    return "*".repeat(length)
}

fun main() {
    val scanner = Scanner(System.`in`)

    println("Enter name and surname: ")
    val fullName = scanner.nextLine()
    println("Enter person's status: ")
    val status = scanner.nextLine()

    drawSignature(fullName, status)
}

fun drawSignature(fullName: String, status: String) {
    val fullNameFont = fullName.map { Font.getCharacter(it) }
    val fullNameWidth = fullNameFont.sumBy { it.getWidth() + 1 } + 5
    val statusWidth = status.length + 6
    val fullWidth = max(fullNameWidth, statusWidth)

    val fullNamePaddingLength = fullWidth - fullNameWidth
    val statusPaddingLength = fullWidth - statusWidth

    val fullNameLeftPad = " ".repeat(fullNamePaddingLength / 2)
    val fullNameRightPad = " ".repeat((fullNamePaddingLength + 1) / 2)
    val statusLeftPad = " ".repeat(statusPaddingLength / 2)
    val statusRightPad = " ".repeat((statusPaddingLength + 1 )/  2)

    println(stars(fullWidth))
    for (i in 0..2) {
        val row = fullNameFont.joinToString(" ") { it.getRow(i) }
        println("*  $fullNameLeftPad$row$fullNameRightPad  *")
    }
    println("*  $statusLeftPad$status$statusRightPad  *")
    println(stars(fullWidth))
}

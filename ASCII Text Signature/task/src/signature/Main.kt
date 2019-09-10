package signature

import java.io.File
import java.lang.Integer.max
import java.util.Scanner

class Font(val height: Int,
           private val characters: Map<Char, Character>) {
    fun getCharacter(c: Char): Character {
        return characters[c] ?: characters[0.toChar()] ?: error("Null char not found?")
    }

    fun getWidth(s: String): Int = s.sumBy { getCharacter(it).width }

    fun getRow(s: String, row: Int, prefix: String = "", postfix: String = ""): String =
            s.map { getCharacter(it).getRow(row) }.joinToString("", prefix, postfix) { it }

    companion object {
        fun loadFromFile(scanner: Scanner): Font {
            val height = scanner.nextInt()
            val expectedChars = scanner.nextInt()
            var i = 0

            val characters = mutableMapOf<Char, Character>()
            while (scanner.hasNextLine() && i++ < expectedChars) {
                val c = Character.loadFromFile(scanner, height)
                characters[c.character] = c
            }

            //Make space and unknown equal in width to 'a'
            val width = characters['a']!!.width
            characters[' '] = Character.createFilled(' ', ' ', width, height)
            characters[0.toChar()] = Character.createFilled('*', '#', width, height)

            return Font(height, characters.toMap())
        }
    }
}

class Character(val character: Char, val width: Int, val data: Array<String>) {
    fun getRow(row: Int): String {
        return data[row]
    }

    companion object {
        fun loadFromFile(scanner: Scanner, height: Int): Character {
            val character = scanner.next().first()
            val width = scanner.nextInt()
            scanner.nextLine()

            val data = mutableListOf<String>()
            for (i in 1..height) {
                data.add(scanner.nextLine())
            }

            return Character(character, width, data.toTypedArray())
        }

        fun createFilled(character: Char, fillChar: Char, width: Int, height: Int): Character {
            val data = mutableListOf<String>()
            for (i in 1..height) {
                data.add(fillChar.toString().repeat(width))
            }

            return Character(character, width, data.toTypedArray())
        }
    }

}

fun main() {
    val scanner = Scanner(System.`in`)

    println("Enter name and surname: ")
    val fullName = scanner.nextLine()
    println("Enter person's status: ")
    val status = scanner.nextLine()

    drawSignature(fullName, status)
}

fun drawSignature(name: String, status: String) {
    val roman = Font.loadFromFile(Scanner(File("resources/roman.txt")))
    val medium = Font.loadFromFile(Scanner(File("resources/medium.txt")))

    val nameWidth = roman.getWidth(name)
    val statusWidth = medium.getWidth(status)
    val fullWidth = max(nameWidth, statusWidth) + 8

    val namePaddingLength = fullWidth - nameWidth - 8
    val statusPaddingLength = fullWidth - statusWidth - 8

    val nameLeftPad = "88  ${" ".repeat(namePaddingLength / 2)}"
    val nameRightPad = "${" ".repeat((namePaddingLength + 1) / 2)}  88"
    val statusLeftPad = "88  ${" ".repeat(statusPaddingLength / 2)}"
    val statusRightPad = "${" ".repeat((statusPaddingLength + 1) / 2)}  88"

    println("8".repeat(fullWidth))
    for (i in 0 until roman.height) {
        println(roman.getRow(name, i, nameLeftPad, nameRightPad))
    }
    for (i in 0 until medium.height) {
        println(medium.getRow(status, i, statusLeftPad, statusRightPad))
    }
    println("8".repeat(fullWidth))
}

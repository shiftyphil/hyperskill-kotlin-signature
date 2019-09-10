
import org.hyperskill.hstest.v5.testcase.CheckResult


class Task3Test : GeneratorTest<InputClue>() {

    override fun generate() = listOf(
            inputCase("Bill Gates\nVIP",
                    hint = "This test corresponds to the example #1."),

            inputCase("Tom Smith\nWorker",
                    hint = "This test corresponds to the example #2."),

            inputCase("Mr Anonimous\nParticipant",
                    hint = "This test corresponds to the example #3."),

            inputCase("X Y\nVeryVeryLoooooooooongSatus",
                    hint = "This test checks a long status with even length."),

            inputCase("X Y\nVeryVeryLooooooooooongSatus", true,
                    "This test checks a long status with uneven length."),

            inputCase("X Y\nStatus  with spaces",
                    hint = "Status should be printed with the " +
                            "same spaces as original, but name " +
                            "and surname shouldn't contain spaces."),

            inputCase("X Y\nStatus with   spaces!!!", true),

            inputCase(('a'..'z').joinToString("") + " Ivan\nHello", true,
                    hint = "This test checks all possible letters.")
    )

    private inline fun checkBadgeBorder(badge: String, onFailure: (msg: String) -> Unit) {
        val lines = badge.split("\n")

        if (lines.map { it.length }.toSet().size != 1) {
            onFailure("Your border is not rectangular.")
            return
        }
        if (lines.size < 2) {
            onFailure("Your border is not rectangular.")
            return
        }
        if (lines.first().any { it != '*' } || lines.last() != lines.first()) {
            onFailure("Your top and bottom edges don't consist of '*'.")
            return
        }
        if (!lines.drop(1).dropLast(1).all { it.startsWith("* ") && it.endsWith(" *") }) {
            onFailure("Your left and right edges don't consist of '*' with one space padding..")
            return
        }
    }

    /** Compare height, indentation and line contents. */
    private inline fun compareBadges(user: String, author: String, onFailure: (msg: String) -> Unit) {
        val userLines = user.split("\n")
        val authorLines = author.split("\n")

        if (userLines.size != authorLines.size) {
            onFailure("Signature height is incorrect: ${userLines.size} lines instead of ${authorLines.size}.")
            return
        }

        userLines.zip(authorLines)
                .forEach { (userStr, authorStr) ->
                    if (userStr.trim('*', ' ') != authorStr.trim('*', ' ')) {
                        onFailure("Some line in your signature is incorrect.")
                        return
                    } else if (userStr != authorStr) {
                        onFailure("Some indentation in your signature is incorrect.")
                        return
                    }
                }
    }


    override fun check(reply: String, clue: InputClue): CheckResult {
        val badgeStart = reply.indexOf('*')
        if (badgeStart == -1) {
            return CheckResult.FALSE("Your output doesn't contain a signature, wrapped in '*' symbols.")
        }

        val userBadge = reply
                .substring(badgeStart)
                .trim('\n', ' ')

        checkBadgeBorder(userBadge) { errorMessage ->
            return CheckResult.FALSE(errorMessage)
        }

        val badge = authors(clue.input)

        compareBadges(userBadge, badge) { errorMessage ->
            val (name, status) = clue.input.split("\n")
            return clue.toFailure("Wrong output for input lines \"$name\" and \"$status\". $errorMessage")
        }

        return CheckResult.TRUE
    }
}

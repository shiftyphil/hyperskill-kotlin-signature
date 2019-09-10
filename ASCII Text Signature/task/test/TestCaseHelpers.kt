import org.hyperskill.hstest.v5.testcase.CheckResult
import org.hyperskill.hstest.v5.testcase.TestCase


/** Default testCase. */
fun <T> testCase(attach: T, input: String) = TestCase<T>().apply {
    setInput(input)
    setAttach(attach)
}

fun cipheredError() = CheckResult(false, "Incorrect output. This is a private test.")

/** Hide error description in private test. */
fun CheckResult.ciphered() =
        if (!isCorrect)
            cipheredError()
        else CheckResult(true, feedback)

class InputClue(
        val input: String,
        val isPrivate: Boolean = false,
        /** Hint will be printed even for private tests. */
        val hint: String? = null
) {

    /** Ciphers [message] or adds a [hint] to the error if it is not null. */
    fun toFailure(message: String): CheckResult {
        if (isPrivate) {
            return CheckResult.FALSE("Incorrect output. This is a private test. " + (hint ?: ""))
        } else {
            return CheckResult.FALSE("$message ${hint ?: ""}")
        }
    }
}

fun inputCase(
        input: String,
        isPrivate: Boolean = false,
        hint: String? = null
) = testCase(InputClue(input, isPrivate, hint), input)



package data

/**
 * Checks if the character is a double quote.
 *
 * @return `true` if the character is a double quote, `false` otherwise.
 */
fun Char.isDoubleQuotes(): Boolean {
    return this == '"'
}

/**
 * Checks if the character is a recognized operator.
 *
 * @return `true` if the character is an operator, `false` otherwise.
 */
fun Char.isOperator(): Boolean {
    // Set of recognized operators
    val operators = setOf('+', '-', '*', '/', '=', '<', '>')

    // Check if the character is in the set of operators
    return this in operators
}

/**
 * Checks if the character is a delimiter.
 *
 * @return `true` if the character is a delimiter, `false` otherwise.
 */
fun Char.isDelimiter(): Boolean {
    // Set of recognized delimiters
    val delimiters =
        setOf('(', ')', '{', '}', '[', ']', ';', ',', ':', '+', '-', '*', '/', '=', '<', '>', " ")

    // Check if the character is in the set of delimiters
    return this in delimiters
}

/**
 * Checks if the string is a keyword.
 *
 * @return `true` if the string is a keyword, `false` otherwise.
 */
fun String.isKeyword(): Boolean {
    // Set of recognized keywords
    val keywords = setOf(
        "Int", "Short", "Long", "Double", "Float", "Boolean", "True", "False", "String", "for",
        "while", "if", "else", "fun", "Unit", "return", "when", "class","is"
    )

    // Check if the trimmed string is in the set of keywords (case-sensitive)
    return this.trim() in keywords
}

/**
 * Checks if the string is a valid number.
 *
 * @return `true` if the string is a number, `false` otherwise.
 */
fun String.isNumber(): Boolean {
    return this.toDoubleOrNull() != null
}

/**
 * Checks whether the given string is a valid identifier.
 *
 * An identifier is considered valid if it starts with a non-null character,
 * and this character is not a delimiter or a digit.
 *
 * @return `true` if the string is a valid identifier, `false` otherwise.
 */
fun String.isValidIdentifier(): Boolean {
    // Retrieves the first character of the string, or returns false if the string is empty.
    val firstChar = this.firstOrNull() ?: return false

    // Checks if the first character is neither a delimiter nor a digit.
    return !firstChar.isDelimiter() && !firstChar.isDigit()
}

/**
 * Checks if the string represents whitespace.
 *
 * @return `true` if the string is a whitespace character (space or newline), `false` otherwise.
 */
fun String.isWhiteSpace(): Boolean {
    return this == " " || this == "\n"
}
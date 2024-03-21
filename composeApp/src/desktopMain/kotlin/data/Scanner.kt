package data

import isDelimiter
import isDoubleQuotes
import isKeyword
import isNumber
import isOperator
import isValidIdentifier
import isWhiteSpace
import models.Token
import models.TokenType

class Scanner(private val code: String): Tokenizable {
    /**
     * Tokenizes the list of words.
     *
     * This function tokenizes the input list of words, categorizing each word into different token types.
     * It creates a list of Token objects based on the classification of the words.
     *
     * @return A list of Token objects representing the tokens obtained from the input list of words.
     */
    override fun tokenize(): List<Token> {
        val delimiters = arrayOf("(", ")", "{", "}", "[", "]", ";", ",", ":", "+", "-", "*", "/", "=", "<", ">", " ")
        val words = code.split(*delimiters)
        return words.flatMap { word ->
            when {
                // If the word is a string literal enclosed in double quotes
                word.contains("\"") && word.endsWith("\"") -> listOf(Token(TokenType.STRING_LITERAL, word))
                // If the word is a keyword
                word.isKeyword() -> listOf(Token(TokenType.KEYWORD, word))
                // If the word is a number
                word.isNumber() -> listOf(Token(TokenType.NUMBER, word))
                // If the word consists of only operators
                word.all { it.isOperator() } -> word.map { Token(TokenType.OPERATOR, it.toString()) }
                // If the word represents whitespace
                word.isWhiteSpace() -> listOf(Token(TokenType.WHITESPACE, word))
                // If the word is a valid identifier
                word.trim().isValidIdentifier() -> listOf(Token(TokenType.IDENTIFIER, word))
                // If the word consists of only delimiters
                word.all { it.isDelimiter() } -> word.map { Token(TokenType.PUNCTUATION, it.toString()) }
                // If the word is unrecognized
                else -> listOf(Token(TokenType.UNKNOWN, word))
            }
        }
    }

    /**
     * Splits the string into parts using the specified delimiters.
     *
     * This method splits the string into parts based on the provided delimiters.
     * It takes into account string literals enclosed in double quotes.
     *
     * @param delimiters An array of delimiters used to split the string.
     * @return A list of strings representing the parts obtained after splitting.
     */
    private fun String.split(vararg delimiters: String): List<String> {
        // Flag to track whether currently inside a string literal
        var isInStringLiteral = false

        // Perform the splitting operation
        return this.fold(mutableListOf()) { parts, currentCharacter ->
            when {
                // If currently inside a string literal, append characters to the current part
                isInStringLiteral -> {
                    parts.apply { set(lastIndex, last() + currentCharacter) }
                    // If encountering a double quote, toggle the flag to indicate end of string literal
                    if (currentCharacter.isDoubleQuotes()) isInStringLiteral = false
                }
                // If encountering a double quote, start a new string literal
                currentCharacter.isDoubleQuotes() -> {
                    isInStringLiteral = true
                    parts.apply { add("\"") }
                }
                // If the current character is a delimiter, add it as a separate part
                delimiters.contains(currentCharacter.toString()) -> {
                    parts.apply { add(currentCharacter.toString()) }
                }
                // If the current character is not a delimiter or inside a string literal, append it to the current part
                else -> {
                    parts.apply {
                        if (isEmpty() || last().lastOrNull()?.isDelimiter() == true) {
                            add("")
                        }
                        set(lastIndex, last() + currentCharacter)
                    }
                }
            }
            parts
        }
    }
}
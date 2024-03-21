package models

enum class TokenType {
    IDENTIFIER,
    NUMBER,
    STRING_LITERAL,
    OPERATOR,
    KEYWORD,
    PUNCTUATION,
    WHITESPACE,
    UNKNOWN,
}
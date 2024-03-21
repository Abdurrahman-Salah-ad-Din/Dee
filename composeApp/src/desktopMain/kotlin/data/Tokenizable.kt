package data

import models.Token

interface Tokenizable {
    fun tokenize(): List<Token>
}
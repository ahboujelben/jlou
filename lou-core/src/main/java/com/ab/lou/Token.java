package com.ab.lou;

import java.util.Objects;

/**
 * Lou token structure.
 */
class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Token) {
            Token that = (Token) obj;
            return this.type.equals(that.type) && this.lexeme.equals(that.lexeme)
                    && Objects.equals(this.lexeme, that.lexeme) && this.line == that.line;
        }
        return false;

    }

    @Override
    public String toString() {
        return type + " " + lexeme + " " + literal;
    }
}

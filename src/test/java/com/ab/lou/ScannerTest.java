package com.ab.lou;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ScannerTest {
    @Test
    void testScanTokens() {
        String code = "//comment \n a =2; print(a);//comment \n b=\"www\"; class classy;";

        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token(TokenType.IDENTIFIER, "a", null, 2));
        tokens.add(new Token(TokenType.EQUAL, "=", null, 2));
        tokens.add(new Token(TokenType.NUMBER, "2", 2.0, 2));
        tokens.add(new Token(TokenType.SEMICOLON, ";", null, 2));

        tokens.add(new Token(TokenType.PRINT, "print", null, 2));
        tokens.add(new Token(TokenType.LEFT_PAREN, "(", null, 2));
        tokens.add(new Token(TokenType.IDENTIFIER, "a", null, 2));
        tokens.add(new Token(TokenType.RIGHT_PAREN, ")", null, 2));
        tokens.add(new Token(TokenType.SEMICOLON, ";", null, 2));

        tokens.add(new Token(TokenType.IDENTIFIER, "b", null, 3));
        tokens.add(new Token(TokenType.EQUAL, "=", null, 3));
        tokens.add(new Token(TokenType.STRING, "\"www\"", "www", 3));
        tokens.add(new Token(TokenType.SEMICOLON, ";", null, 3));

        tokens.add(new Token(TokenType.CLASS, "class", null, 3));
        tokens.add(new Token(TokenType.IDENTIFIER, "classy", null, 3));
        tokens.add(new Token(TokenType.SEMICOLON, ";", null, 3));

        tokens.add(new Token(TokenType.EOF, "", null, 3));

        assertEquals(tokens, new Scanner(code).scanTokens());
    }
}

package com.ab.lou;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger("client");

    static boolean hadError = false;
    static boolean hadRuntimeError = false;

    static void error(int line, String message) {
        report(line, "", message);
    }

    static void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }

    static void runtimeError(LouExceptions.RuntimeError error) {
        report(error.token.line, " at '" + error.token.lexeme + "'", error.getMessage());
        hadRuntimeError = true;
    }

    private static void report(int line, String where, String message) {
        logger.error("[line {}] Error{}: {}", line, where, message);
        hadError = true;
    }

    private ErrorHandler() {
    }
}

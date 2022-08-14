package com.ab.lou;

import org.slf4j.Logger;

/**
 * Report information, and compile/runtime errors to users.
 */
class Reporter {
    private Logger logger;

    Reporter(Logger logger) {
        this.logger = logger;
    }

    boolean hadError = false;
    boolean hadRuntimeError = false;

    void info(String message) {
        logger.info(message);
    }

    void error(String message) {
        logger.error(message);
    }

    void error(int line, String message) {
        report(line, "", message);
    }

    void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }

    void runtimeError(LouExceptions.RuntimeError error) {
        report(error.token.line, " at '" + error.token.lexeme + "'", error.getMessage());
        hadRuntimeError = true;
    }

    private void report(int line, String where, String message) {
        logger.error("[line {}] Error{}: {}", line, where, message);
        hadError = true;
    }
}

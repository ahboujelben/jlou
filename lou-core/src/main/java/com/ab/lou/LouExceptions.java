package com.ab.lou;

class LouExceptions {
    private LouExceptions() {
    }

    static class ParseError extends RuntimeException {
    }

    static class RuntimeError extends RuntimeException {
        final Token token;

        RuntimeError(Token token, String message) {
            super(message);
            this.token = token;
        }
    }

    static class Break extends RuntimeException {
        Break() {
            super();
        }
    }

    static class Return extends RuntimeException {
        final Object value;

        Return(Object value) {
            super();
            this.value = value;
        }
    }
}

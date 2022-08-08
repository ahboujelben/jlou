package com.ab.lou;

class LouExceptions {
    private LouExceptions() {
    }

    static class RuntimeError extends RuntimeException {
        final Token token;

        RuntimeError(Token token, String message) {
            super(message);
            this.token = token;
        }
    }

    static class Break extends RuntimeError {
        Break(Token token) {
            super(token, "'break' can only be called inside loops.");
        }
    }

    static class Return extends RuntimeError {
        final Object value;

        Return(Token token, Object value) {
            super(token, "'return' can only be called inside callables.");
            this.value = value;
        }
    }
}

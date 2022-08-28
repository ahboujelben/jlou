package com.ab.lou;

import java.util.HashMap;
import java.util.Map;

class LouInstance {
    private LouClass klass;
    private final Map<String, Object> fields = new HashMap<>();

    LouInstance(LouClass klass) {
        this.klass = klass;
    }

    Object get(Token name) {
        if (fields.containsKey(name.lexeme)) {
            return fields.get(name.lexeme);
        }

        LouFunction method = klass.findMethod(name.lexeme);
        if (method != null) {
            return method.bind(this);
        }

        throw new LouExceptions.RuntimeError(name, "Undefined property '" + name.lexeme + "'.");
    }

    void set(Token name, Object value) {
        fields.put(name.lexeme, value);
    }

    @Override
    public String toString() {
        return "<class " + klass.name + " instance>";
    }
}
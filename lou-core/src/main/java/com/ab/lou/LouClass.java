package com.ab.lou;

import java.util.List;
import java.util.Map;

class LouClass implements LouCallable {
    final String name;
    final LouClass superclass;
    private final Map<String, LouFunction> methods;

    LouClass(String name, LouClass superclass, Map<String, LouFunction> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }

    LouFunction findMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }

        if (superclass != null) {
            return superclass.findMethod(name);
        }

        return null;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        LouInstance instance = new LouInstance(this);

        LouFunction initializer = findMethod("init");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }

        return instance;
    }

    @Override
    public int arity() {
        LouFunction initializer = findMethod("init");
        if (initializer == null) {
            return 0;
        }
        return initializer.arity();
    }

    @Override
    public String toString() {
        return "<class " + name + ">";
    }
}
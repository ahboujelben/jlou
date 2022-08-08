package com.ab.lou;

import java.util.List;

interface LouCallable {
    int arity();

    Object call(Interpreter interpreter, List<Object> arguments);
}

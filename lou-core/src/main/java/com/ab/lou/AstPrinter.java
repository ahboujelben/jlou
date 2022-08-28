package com.ab.lou;

import com.ab.lou.Expr.Assign;
import com.ab.lou.Expr.Call;
import com.ab.lou.Expr.Get;
import com.ab.lou.Expr.Logical;
import com.ab.lou.Expr.Set;
import com.ab.lou.Expr.This;
import com.ab.lou.Expr.Variable;

/**
 * Prints Lou expressions in a readable format.
 * Only used for debugging.
 */
class AstPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitAssignExpr(Assign expr) {
        return parenthesize(expr.name.lexeme + "=", expr.value);
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitCallExpr(Call expr) {
        StringBuilder builder = new StringBuilder();

        builder.append("(function ").append(expr.callee.toString()).append(":");
        for (Expr arg : expr.arguments) {
            builder.append(", ");
            builder.append(print(arg));
        }
        builder.append(")");

        return builder.toString();
    }

    @Override
    public String visitGetExpr(Get expr) {
        return parenthesize("get", expr);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null)
            return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitLogicalExpr(Logical expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitSetExpr(Set expr) {
        return parenthesize("set", expr);
    }

    @Override
    public String visitThisExpr(This expr) {
        return "this";
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    @Override
    public String visitVariableExpr(Variable expr) {
        return expr.name.lexeme;
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(print(expr));
        }
        builder.append(")");

        return builder.toString();
    }
}

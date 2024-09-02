/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class ExpressionsList extends Node {
    private final List<Expression> expressions = new ArrayList<>();

    public ExpressionsList(int line) {
        super(line);
    }

    public void add(Expression expression) {
        expressions.add(expression);
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public int getColumn() {
        return -1;
    }
}


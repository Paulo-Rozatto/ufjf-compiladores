package br.ufjf.estudante.ast;

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
}


package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class ExpressionArithmetic extends Expression {
    private final String op;
    private final Expression left;
    private final Expression right;

    public ExpressionArithmetic(String op, Expression left, Expression right, int line) {
        super(line);
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public Object evaluate() {
        return null;
    }
}

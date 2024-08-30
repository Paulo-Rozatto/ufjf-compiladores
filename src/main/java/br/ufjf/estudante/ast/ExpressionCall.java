package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class ExpressionCall extends Expression {
    private final ExpressionsList params;
    private final Expression modifier;

    public ExpressionCall(ExpressionsList params, Expression modifier, int line) {
        super(line);
        this.params = params;
        this.modifier = modifier;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

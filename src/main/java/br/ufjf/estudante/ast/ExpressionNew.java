package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class ExpressionNew extends Expression {
    private final Type type;
    private final Expression exp;

    public ExpressionNew(Type type, int line) {
        super(line);
        this.type = type;
        this.exp = null;
    }

    public ExpressionNew(Type type, Expression exp, int line) {
        super(line);
        this.type = type;
        this.exp = exp;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

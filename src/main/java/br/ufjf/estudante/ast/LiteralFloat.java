package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class LiteralFloat extends Expression {
    private final float value;

    public LiteralFloat(float value, int line) {
        super(line);
        this.value = value;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public Object evaluate() {
        return null;
    }
}


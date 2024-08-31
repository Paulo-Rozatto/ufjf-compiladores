package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class LiteralInt extends Expression {
    private final int value;

    public LiteralInt(int value, int line) {
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

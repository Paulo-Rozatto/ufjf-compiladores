package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class LiteralChar extends Expression {
    private final char value;

    public LiteralChar(char value, int line) {
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

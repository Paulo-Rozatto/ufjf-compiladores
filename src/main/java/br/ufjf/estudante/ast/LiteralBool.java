package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class LiteralBool extends Expression {
    private final Boolean value;

    public LiteralBool(Boolean value, int line) {
        super(line);
        this.value = value;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

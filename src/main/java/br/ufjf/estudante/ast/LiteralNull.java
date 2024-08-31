package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class LiteralNull extends Expression {

    public LiteralNull(int line) {
        super(line);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public Object evaluate() {
        return null;
    }
}

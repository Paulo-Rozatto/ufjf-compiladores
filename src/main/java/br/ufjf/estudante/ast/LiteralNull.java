package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class LiteralNull extends Literal {

    public LiteralNull(int line) {
        super(line);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type getType() {
        return null;
    }

    @Override
    public Literal equals(Literal arg) {
        return new LiteralBool(arg.getClass() == LiteralNull.class, lineNumber);
    }

    @Override
    public Literal notEquals(Literal arg) {
        return new LiteralBool(arg.getClass() != LiteralNull.class, lineNumber);
    }
}

package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class LiteralChar extends Literal {
    private final char value;

    public LiteralChar(char value, int line) {
        super(line);
        this.value = value;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public char getValue() {
        return value;
    }

    @Override
    public Literal evaluate() {
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Literal equals(Literal arg) {
        if (arg.getClass() == LiteralChar.class) {
            boolean result = value == ((LiteralChar) arg).getValue();
            return new LiteralBool(result, lineNumber);
        }

        return super.add(arg);
    }

    @Override
    public Literal notEquals(Literal arg) {
        if (arg.getClass() == LiteralChar.class) {
            boolean result = value != ((LiteralChar) arg).getValue();
            return new LiteralBool(result, lineNumber);
        }

        return super.add(arg);
    }
}

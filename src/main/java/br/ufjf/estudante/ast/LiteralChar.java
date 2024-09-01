package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

import java.util.Objects;

public class LiteralChar extends Literal {
    private final String value;

    public LiteralChar(String value, int line) {
        super(line);
        this.value = value;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return new TypePrimitive(this.getClass(), lineNumber);
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
            boolean result = Objects.equals(value, ((LiteralChar) arg).getValue());
            return new LiteralBool(result, lineNumber);
        }

        return super.add(arg);
    }

    @Override
    public Literal notEquals(Literal arg) {
        if (arg.getClass() == LiteralChar.class) {
            boolean result = !Objects.equals(value, ((LiteralChar) arg).getValue());
            return new LiteralBool(result, lineNumber);
        }

        return super.add(arg);
    }
}

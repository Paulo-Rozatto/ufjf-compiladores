package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class LiteralBool extends Literal {
    private final Boolean value;

    public LiteralBool(Boolean value, int line) {
        super(line);
        this.value = value;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public Literal evaluate() {
        return this;
    }

    @Override
    public Literal and(Literal arg) {
        if (arg.getClass() == LiteralBool.class) {
            boolean result = value && ((LiteralBool) arg).getValue();
            return new LiteralBool(result, lineNumber);
        }

        return super.add(arg);
    }

//    @Override
//    public Literal or(Literal arg) {
//        if(arg.getClass() == LiteralBool.class) {
//            boolean result = value || ((LiteralBool) arg).getValue();
//            return new LiteralBool(result, lineNumber);
//        }
//
//        return super.add(arg);
//    }

    @Override
    public Literal not() {
        return new LiteralBool(!value, lineNumber);
    }

    @Override
    public Literal equals(Literal arg) {
        if (arg.getClass() == LiteralBool.class) {
            boolean result = value == ((LiteralBool) arg).getValue();
            return new LiteralBool(result, lineNumber);
        }

        return super.add(arg);
    }

    @Override
    public Literal notEquals(Literal arg) {
        if (arg.getClass() == LiteralBool.class) {
            boolean result = value != ((LiteralBool) arg).getValue();
            return new LiteralBool(result, lineNumber);
        }

        return super.add(arg);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

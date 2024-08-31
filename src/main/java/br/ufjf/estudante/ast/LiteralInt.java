package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class LiteralInt extends Literal {
    private final int value;

    public LiteralInt(int value, int line) {
        super(line);
        this.value = value;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public int getValue() {
        return value;
    }

    @Override
    public Literal evaluate() {
        return this;
    }

    @Override
    public Literal equals(Literal arg) {
        if (arg.getClass() == LiteralInt.class) {
            boolean result = value == ((LiteralInt) arg).getValue();
            return new LiteralBool(result, lineNumber);
        }
        return super.add(arg);
    }

    @Override
    public Literal notEquals(Literal arg) {
        if (arg.getClass() == LiteralInt.class) {
            boolean result = value != ((LiteralInt) arg).getValue();
            return new LiteralBool(result, lineNumber);
        }
        return super.add(arg);
    }

    @Override
    public Literal smaller(Literal arg) {
        if (arg.getClass() == LiteralInt.class) {
            boolean result = value < ((LiteralInt) arg).getValue();
            return new LiteralBool(result, lineNumber);
        }
        return super.add(arg);
    }

    @Override
    public Literal add(Literal arg) {
        if (arg.getClass() == LiteralInt.class) {
            int result = value + ((LiteralInt) arg).getValue();
            return new LiteralInt(result, lineNumber);
        }
//        else if (arg.getClass() == LiteralFloat.class) {
//            float result = value + ((LiteralFloat) arg).getValue();
//            return new LiteralFloat(result, lineNumber);
//        }

        return super.add(arg);
    }


    @Override
    public Literal sub(Literal arg) {
        if (arg.getClass() == LiteralInt.class) {
            int result = value - ((LiteralInt) arg).getValue();
            return new LiteralInt(result, lineNumber);
        }
        return super.add(arg);
    }

    @Override
    public Literal mul(Literal arg) {
        if (arg.getClass() == LiteralInt.class) {
            int result = value * ((LiteralInt) arg).getValue();
            return new LiteralInt(result, lineNumber);
        }
        return super.add(arg);
    }

    @Override
    public Literal div(Literal arg) {
        if (arg.getClass() == LiteralInt.class && ((LiteralInt) arg).getValue() != 0) {
            int result = value / ((LiteralInt) arg).getValue();
            return new LiteralInt(result, lineNumber);
        }
        return super.add(arg);
    }

    @Override
    public Literal mod(Literal arg) {
        if (arg.getClass() == LiteralInt.class) {
            int result = value % ((LiteralInt) arg).getValue();
            return new LiteralInt(result, lineNumber);
        }
        return super.add(arg);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

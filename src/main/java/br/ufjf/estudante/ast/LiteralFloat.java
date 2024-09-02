/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class LiteralFloat extends Literal {
    private final float value;

    public LiteralFloat(float value, int line) {
        super(line);
        this.value = value;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public float getValue() {
        return value;
    }

    public Type getType() {
        return new TypePrimitive(this.getClass(), lineNumber);
    }

    @Override
    public Literal equals(Literal arg) {
        if (arg.getClass() == LiteralFloat.class) {
            boolean result = value == ((LiteralFloat) arg).getValue();
            return new LiteralBool(result, lineNumber);
        }
        return super.add(arg);
    }

    @Override
    public Literal notEquals(Literal arg) {
        if (arg.getClass() == LiteralFloat.class) {
            boolean result = value != ((LiteralFloat) arg).getValue();
            return new LiteralBool(result, lineNumber);
        }
        return super.add(arg);
    }

    @Override
    public Literal smaller(Literal arg) {
        if (arg.getClass() == LiteralFloat.class) {
            boolean result = value < ((LiteralFloat) arg).getValue();
            return new LiteralBool(result, lineNumber);
        }
        return super.add(arg);
    }

    public Literal add(Literal arg) {
        if (arg.getClass() == LiteralFloat.class) {
            float result = value + ((LiteralFloat) arg).getValue();
            return new LiteralFloat(result, lineNumber);
        }

        return super.add(arg);
    }

    @Override
    public Literal sub(Literal arg) {
        if (arg.getClass() == LiteralFloat.class) {
            float result = value - ((LiteralFloat) arg).getValue();
            return new LiteralFloat(result, lineNumber);
        }

        return super.add(arg);
    }

    public Literal mul(Literal arg) {
        if (arg.getClass() == LiteralFloat.class) {
            float result = value * ((LiteralFloat) arg).getValue();
            return new LiteralFloat(result, lineNumber);
        }

        return super.add(arg);
    }

    @Override
    public Literal div(Literal arg) {
        if (arg.getClass() == LiteralFloat.class && ((LiteralFloat) arg).getValue() != 0) {
            float result = value / ((LiteralFloat) arg).getValue();
            return new LiteralFloat(result, lineNumber);
        }

        return super.add(arg);
    }

    @Override
    public Literal evaluate() {
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}


package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

import java.lang.reflect.Array;

public class ExpressionNew extends Expression {
    private final Type type;
    private final Expression exp;

    public ExpressionNew(Type type, int line) {
        super(line);
        this.type = type;
        this.exp = null;
    }

    public ExpressionNew(Type type, Expression exp, int line) {
        super(line);
        this.type = type;
        this.exp = exp;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type getType() {
        return type;
    }

    public Expression getExp() {
        return exp;
    }

    @Override
    public Literal evaluate() {
        if (exp != null) {
            int size = ((LiteralInt) exp.evaluate()).getValue();

            Object array = Array.newInstance(((TypePrimitive) type).getC(), size);

            for (int i = 1; i < type.getDimensions(); i++) {
                Object tempArray = Array.newInstance(array.getClass(), size);
                for (int j = 0; j < size; j++) {
                    Array.set(tempArray, j, array);
                }
                array = tempArray;
            }

            return new LiteralArray(array, type.getDimensions(), size, lineNumber);
        }
        return null;
    }
}

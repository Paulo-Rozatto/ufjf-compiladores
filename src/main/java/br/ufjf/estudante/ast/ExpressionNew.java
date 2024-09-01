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

            System.out.println(((TypePrimitive) type).getC());
            Object array = Array.newInstance(((TypePrimitive) type).getC(), size); // Cria o array da primeira dimensão

            for (int i = 1; i < type.getDimensions(); i++) {
                Object tempArray = Array.newInstance(array.getClass()); // Cria o próximo nível
//                for (int j = 0; j < size; j++) {
//                    Array.set(tempArray, j, array); // Configura cada elemento do array atual como uma referência ao array anterior
//                }
                array = tempArray; // Atualiza a referência para o novo array aninhado
            }

            return new LiteralArray(array, type.getDimensions(), size, lineNumber);
        }
        return null;
    }
}

/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class TypePrimitive extends Type {
    private final Class<?> c;

    public TypePrimitive(Class<?> c, int line) {
        super(line);
        this.c = c;
    }

    public Class<?> getC() {
        return c;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public int getColumn() {
        return -1;
    }
}

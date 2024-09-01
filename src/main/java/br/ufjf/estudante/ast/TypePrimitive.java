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
}

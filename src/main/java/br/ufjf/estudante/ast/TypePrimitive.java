package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class TypePrimitive<T> extends Type {
    public TypePrimitive(int line) {
        super(line);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

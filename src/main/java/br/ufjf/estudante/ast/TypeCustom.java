package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class TypeCustom extends Type {
    public TypeCustom(int line) {
        super(line);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

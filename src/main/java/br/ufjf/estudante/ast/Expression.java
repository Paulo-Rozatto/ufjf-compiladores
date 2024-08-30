package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public abstract class Expression extends Node {
    public Expression(int line) {
        super(line);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public abstract Object evaluate();
}

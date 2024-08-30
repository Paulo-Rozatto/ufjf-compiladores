package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public abstract class Type extends Node {
    protected int dimensions;

    public Type(int line) {
        super(line);
        dimensions = 1;
    }

    public void increaseDimensions() {
        dimensions += 1;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

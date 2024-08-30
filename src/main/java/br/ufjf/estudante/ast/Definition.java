package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

abstract public class Definition extends Node {
    public Definition(int line) {
        super(line);
    }

    public abstract String getId();

    public void accept(Visitor v) {
        v.visit(this);
    }
}

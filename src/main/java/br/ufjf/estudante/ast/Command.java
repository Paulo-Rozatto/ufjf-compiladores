package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class Command extends Node {
    public Command(int line) {
        super(line);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

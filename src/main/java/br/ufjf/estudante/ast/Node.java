package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;


abstract public class Node {
    protected int lineNumber;

    public Node(int line) {
        lineNumber = line;
    }

    public int getLine() {
        return lineNumber;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
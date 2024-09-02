/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;


abstract public class Node extends SuperNode {
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
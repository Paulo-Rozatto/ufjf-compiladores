/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public abstract class Type extends Node {
    private int dimensions;

    public Type(int line) {
        super(line);
        dimensions = 1;
    }

    public void increaseDimensions() {
        dimensions += 1;
    }

    public int getDimensions() {
        return dimensions;
    }

    public abstract Class<?> getC();

    public void accept(Visitor v) {
        v.visit(this);
    }
}

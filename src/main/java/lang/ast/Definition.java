/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package lang.ast;

import br.ufjf.estudante.visitor.Visitor;

abstract public class Definition extends Node {
    public Definition(int line) {
        super(line);
    }

    public abstract String getId();

    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public int getColumn() {
        return -1;
    }
}

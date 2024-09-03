/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package lang.ast;

import br.ufjf.estudante.visitor.Visitor;

public abstract class Expression extends Node {
    public Expression(int line) {
        super(line);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public abstract Literal evaluate();

    @Override
    public int getColumn() {
        return -1;
    }
}

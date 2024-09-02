/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package lang.ast;

import br.ufjf.estudante.visitor.Visitor;

public class Command extends Node {
    public Command(int line) {
        super(line);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public int getColumn() {
        return -1;
    }
}

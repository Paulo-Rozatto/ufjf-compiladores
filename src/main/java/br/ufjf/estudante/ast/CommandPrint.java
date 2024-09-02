/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class CommandPrint extends Command {
    private final Expression expression;

    public CommandPrint(Expression expression, int line) {
        super(line);
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public int getColumn() {
        return -1;
    }
}

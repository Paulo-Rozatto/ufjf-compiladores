/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class CommandReturn extends Command {
    private final ExpressionsList returns;

    public CommandReturn(ExpressionsList returns, int line) {
        super(line);
        this.returns = returns;
    }

    public ExpressionsList getReturns() {
        return returns;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public int getColumn() {
        return -1;
    }
}

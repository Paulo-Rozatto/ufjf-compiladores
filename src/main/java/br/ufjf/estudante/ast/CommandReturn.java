package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class CommandReturn extends Command {
    private final ExpressionsList returns;

    public CommandReturn(ExpressionsList returns, int line) {
        super(line);
        this.returns = returns;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

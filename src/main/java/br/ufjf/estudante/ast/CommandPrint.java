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
}

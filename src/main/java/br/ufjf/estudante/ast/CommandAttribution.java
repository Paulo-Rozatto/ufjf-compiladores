package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class CommandAttribution extends Command {
    private final LValue lValue;
    private final Expression expression;

    public CommandAttribution(LValue lValue, Expression expression, int line) {
        super(line);
        this.lValue = lValue;
        this.expression = expression;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

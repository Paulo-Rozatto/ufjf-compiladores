package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class CommandIterate extends Command {
    private final Expression expression;
    private final Command command;

    public CommandIterate(Expression expression, Command command, int line) {
        super(line);
        this.expression = expression;
        this.command = command;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

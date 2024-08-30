package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class CommandIf extends Command {
    private final Expression expression;
    private final Command then;
    private final Command otherwise;

    public CommandIf(Expression expression, Command then, int line) {
        super(line);
        this.expression = expression;
        this.then = then;
        this.otherwise = null;
    }

    public CommandIf(Expression expression, Command then, Command otherwise, int line) {
        super(line);
        this.expression = expression;
        this.then = then;
        this.otherwise = otherwise;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

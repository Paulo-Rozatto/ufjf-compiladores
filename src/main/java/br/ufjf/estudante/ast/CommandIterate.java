package br.ufjf.estudante.ast;

public class CommandIterate extends Command {
    private final Expression expression;
    private final Command command;

    public CommandIterate(Expression expression, Command command, int line) {
        super(line);
        this.expression = expression;
        this.command = command;
    }
}

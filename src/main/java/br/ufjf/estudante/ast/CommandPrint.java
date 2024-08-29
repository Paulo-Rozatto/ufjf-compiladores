package br.ufjf.estudante.ast;

public class CommandPrint extends  Command{
    private final Expression expression;

    public CommandPrint(Expression expression, int line) {
        super(line);
        this.expression = expression;
    }
}

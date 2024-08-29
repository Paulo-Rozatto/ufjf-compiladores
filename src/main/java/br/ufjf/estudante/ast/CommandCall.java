package br.ufjf.estudante.ast;

public class CommandCall extends Command {
    private final ExpressionsList params;
    private final Object returnVars; // todo: type retTypes

    public CommandCall(ExpressionsList params, Object returnVars, int line) {
        super(line);
        this.params = params;
        this.returnVars = returnVars;
    }
}

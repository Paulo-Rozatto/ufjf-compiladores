package br.ufjf.estudante.ast;

public class CommandAttribution extends Command {
    private final LValue lValue;
    private final Expression expression;

    public CommandAttribution(LValue lValue, Expression expression, int line) {
        super(line);
        this.lValue = lValue;
        this.expression = expression;
    }
}

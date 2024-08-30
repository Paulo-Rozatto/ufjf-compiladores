package br.ufjf.estudante.ast;

public class ExpressionCall extends Expression {
    private final ExpressionsList params;
    private final Expression modifier;

    public ExpressionCall(ExpressionsList params, Expression modifier, int line) {
        super(line);
        this.params = params;
        this.modifier = modifier;
    }
}

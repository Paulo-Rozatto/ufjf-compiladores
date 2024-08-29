package br.ufjf.estudante.ast;

public class CommandReturn extends Command{
    private final ExpressionsList returns;

    public CommandReturn(ExpressionsList returns, int line) {
        super(line);
        this.returns = returns;
    }
}

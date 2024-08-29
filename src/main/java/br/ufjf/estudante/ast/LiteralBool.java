package br.ufjf.estudante.ast;

public class LiteralBool extends Expression {
    private final Boolean value;

    public LiteralBool(Boolean value, int line) {
        super(line);
        this.value = value;
    }
}

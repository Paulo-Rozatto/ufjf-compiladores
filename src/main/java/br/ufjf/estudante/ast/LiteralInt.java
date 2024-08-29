package br.ufjf.estudante.ast;

public class LiteralInt extends Expression {
    private final int value;

    public LiteralInt(int value, int line) {
        super(line);
        this.value = value;
    }
}

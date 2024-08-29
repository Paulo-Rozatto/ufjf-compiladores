package br.ufjf.estudante.ast;

public class LiteralChar extends Expression {
    private final char value;

    public LiteralChar(char value, int line) {
        super(line);
        this.value = value;
    }
}

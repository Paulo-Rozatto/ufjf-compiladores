package br.ufjf.estudante.ast;

public class LiteralFloat extends Expression {
    private final float value;

    public LiteralFloat(float value, int line) {
        super(line);
        this.value = value;
    }
}


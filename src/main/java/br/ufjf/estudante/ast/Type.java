package br.ufjf.estudante.ast;

public abstract class Type extends Node {
    protected int dimensions;

    public Type(int line) {
        super(line);
        dimensions = 1;
    }

    public void increaseDimensions() {
        dimensions += 1;
    }
}

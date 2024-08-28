package br.ufjf.estudante.ast;

abstract public class Definition extends Node {
    public Definition(int line) {
        super(line);
    }

    public abstract String getId();
}

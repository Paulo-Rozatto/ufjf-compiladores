package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

import java.util.HashMap;

public class Declarations extends Node {
    private final HashMap<String, Type> innerVariables = new HashMap<>();

    public Declarations(int line) {
        super(line);
    }

    public void add(String id, Type type) {
        innerVariables.put(id, type);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

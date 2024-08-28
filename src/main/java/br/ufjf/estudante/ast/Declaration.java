package br.ufjf.estudante.ast;

import java.util.HashMap;

public class Declaration extends Definition {
    private final HashMap<String, Type> innerVariables = new HashMap<>();

    public Declaration(int line) {
        super(line);
    }

    public void add(String id, Type type) {
        innerVariables.put(id, type);
    }
}

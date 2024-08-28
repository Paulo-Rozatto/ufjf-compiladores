package br.ufjf.estudante.ast;

import java.util.HashMap;
import java.util.Map;

public class Params extends Node {
    private final Map<String, Type> params = new HashMap<>();

    public Params(int line) {
        super(line);
    }

    public void add(String id, Type type) {
        params.put(id, type);
    }
}

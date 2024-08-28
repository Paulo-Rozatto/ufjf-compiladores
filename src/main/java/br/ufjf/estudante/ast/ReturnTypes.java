package br.ufjf.estudante.ast;

import java.util.ArrayList;
import java.util.List;

public class ReturnTypes extends Node {
    private final List<Type> types = new ArrayList<>();

    public ReturnTypes(int line) {
        super(line);
    }

    public void add(Type type) {
        types.add(type);
    }
}

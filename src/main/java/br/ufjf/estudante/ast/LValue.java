package br.ufjf.estudante.ast;

import java.util.ArrayList;
import java.util.List;

public class LValue extends Expression {
    private final String id;
    private final List<Object> modifiers = new ArrayList<>();

    public LValue(String id, int line) {
        super(line);
        this.id = id;
    }

    public void addModifier(String id) {
        modifiers.add(id);
    }

    public void addModifier(Expression exp) {
        modifiers.add(exp);
    }
}

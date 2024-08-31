package br.ufjf.estudante.ast;

import br.ufjf.estudante.util.Pair;
import br.ufjf.estudante.visitor.Visitor;
import br.ufjf.estudante.visitor.VisitorInterpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LValue extends Expression {
    private final String id;
    private final List<Object> modifiers = new ArrayList<>();
    private Map<String, Pair<Type, Literal>> env;

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


    public void accept(Visitor v) {
        if (v.getClass() == VisitorInterpreter.class) {
            env = ((VisitorInterpreter) v).getEnv();
        } else {
            env = null;
        }

        v.visit(this);
    }

    @Override
    public Literal evaluate() {
        if (env == null) {
            return null;
        }

        Pair<Type, Literal> var = env.get(id);

        return var == null ? null : var.getSecond();
    }

    public String getId() {
        return id;
    }
}

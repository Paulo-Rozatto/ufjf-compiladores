package br.ufjf.estudante.ast;

import br.ufjf.estudante.util.Pair;
import br.ufjf.estudante.visitor.Visitor;
import br.ufjf.estudante.visitor.VisitorInterpreter;

import java.lang.reflect.Array;
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

    public void set(Literal value) {
        if (modifiers.isEmpty()) {
            Pair<Type, Literal> ref = env.get(id);
            if (ref == null) {
                ref = new Pair<>(value.getType(), value);
                env.put(id, ref);
            } else if (ref.getSecond().getClass() == value.getClass()) {
                ref.setSecond(value);
            } else {
                throw new RuntimeException(String.format("Não se pode atribuir tipo %s em variável %s", value.getClass(), ref.getSecond().getClass()));
            }
            return;
        }

        Pair<Type, Literal> ref = env.get(id);
        Object currentObject = ((LiteralArray) ref.getSecond()).getArray();
        Object modifier;

        for (int i = 0; i < modifiers.size() - 1; i++) {
            modifier = modifiers.get(i);

            if (modifier.getClass() == LiteralInt.class) {
                currentObject = Array.get(currentObject, ((LiteralInt) modifier).getValue());
            } else {
                throw new RuntimeException("Modificador invalido: " + modifier);
            }
        }

        int index = ((LiteralInt) modifiers.get(modifiers.size() - 1)).getValue();
        if (value.getClass() == LiteralArray.class) {
            Array.set(currentObject, index, ((LiteralArray) value).getArray());

        } else {
            Array.set(currentObject, index, value);
        }
    }


    @Override
    public Literal evaluate() {
        if (env == null) {
            return null;
        }

        Pair<Type, Literal> var = env.get(id);

        if (var == null) {
            return null;
        }

        if (modifiers.isEmpty()) {
            return var.getSecond();
        }

        Object currentArray = ((LiteralArray) var.getSecond()).getArray();
        for (Object modifier : modifiers) {
            int index = ((LiteralInt) modifier).getValue();
            currentArray = Array.get(currentArray, index);

        }

        Object value = currentArray;

        if (value.getClass().isArray()) {
            // todo: remover esses parametros de dimensao nao usados
            return new LiteralArray(value, 1, 1, 1);
        }

        return (Literal) value;
    }

    public String getId() {
        return id;
    }
}

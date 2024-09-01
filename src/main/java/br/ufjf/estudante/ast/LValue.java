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
            }
            return;
        }

//        Object currentArray = array;
//        for (int i = 0; i < indices.length - 1; i++) {
//            currentArray = Array.get(currentArray, indices[i]);
//        }
//        Array.set(currentArray, indices[indices.length - 1], value)

        System.out.println("Mod: " + id + " - " + modifiers);

        Pair<Type, Literal> ref = env.get(id);
        Object currentArray = ((LiteralArray) ref.getSecond()).getArray();
        int index = ((LiteralInt) modifiers.get(0)).getValue();
//        for (Object modifier : modifiers) {
//            if (modifier.getClass() == LiteralInt.class) {
//                index = ((LiteralInt) modifier).getValue();
//                currentArray = Array.get(currentArray, index);
//            } else {
//                throw new RuntimeException("Modificador invalido: " + modifier);
//            }
//        }
//        System.out.println(currentArray + "[" + index + "] <- " + value.getClass());
        Array.set(currentArray, index, value);

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

        int index = ((LiteralInt) modifiers.get(0)).getValue();
        Object currentArray = ((LiteralArray) var.getSecond()).getArray();

        return (Literal) Array.get(currentArray, index);
    }

    public String getId() {
        return id;
    }
}

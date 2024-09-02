/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

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

    public List<Type> getTypes() {
        return types;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

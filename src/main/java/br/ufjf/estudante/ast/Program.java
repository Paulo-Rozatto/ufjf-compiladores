package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class Program extends Node {
    final private DefinitionsList defList;

    public Program(DefinitionsList d, int ln) {
        super(ln);
        defList = d;
    }

    public void add(Definition d) {
        defList.add(d);
    }

    public Definition get(int i) {
        return defList.get(i);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

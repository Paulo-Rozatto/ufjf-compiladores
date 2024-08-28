package br.ufjf.estudante.ast;

public class Program extends Node {
    final private DefinitionList defList;

    public Program(DefinitionList d, int ln) {
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

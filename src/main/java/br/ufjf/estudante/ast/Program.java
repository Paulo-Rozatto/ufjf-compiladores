package br.ufjf.estudante.ast;

import java.util.ArrayList;

public class Program extends Node {
//    final private ArrayList<Def> defList = new ArrayList<>();
    final private DefList defList;

    public Program(DefList d, int ln) {
        super(ln);
        defList = d;
    }

//    public void add(Def d) {
//        defList.add(d);
//    }
//
    public Def get(int i) {
        return defList.get(i);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

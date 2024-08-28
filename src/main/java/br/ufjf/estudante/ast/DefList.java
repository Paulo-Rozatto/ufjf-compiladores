package br.ufjf.estudante.ast;

import java.util.ArrayList;
import java.util.List;

public class DefList {
    private final List<Def> defList;

    public DefList() {
        defList = new ArrayList<>();
    }

    public DefList(Def d) {
        defList = new ArrayList<>();
        defList.add(d);
    }

    public void add(Def d) {
        defList.add(d);
    }

    public Def get(int i) {
        return defList.get(i);
    }


}

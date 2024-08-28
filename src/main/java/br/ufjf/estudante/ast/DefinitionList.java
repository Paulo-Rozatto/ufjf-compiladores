package br.ufjf.estudante.ast;

import java.util.ArrayList;
import java.util.List;

public class DefinitionList extends Node{
    private final List<Definition> defList = new ArrayList<>();

    public DefinitionList(int line) {
        super(line);
    }

    public DefinitionList(Definition d, int line) {
        super(line);
        defList.add(d);
    }

    public void add(Definition d) {
        defList.add(d);
    }

    public Definition get(int i) {
        return defList.get(i);
    }


}

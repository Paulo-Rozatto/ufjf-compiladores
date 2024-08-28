package br.ufjf.estudante.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefinitionsList extends Node{
    private final Map<String, Definition> defList = new HashMap<>();

    public DefinitionsList(int line) {
        super(line);
    }

    public DefinitionsList(Definition d, int line) {
        super(line);
        defList.put(d.getId(), d);
    }

    public void add(Definition d) {
        defList.put(d.getId(), d);
    }

    public Definition get(int i) {
        return defList.get(i);
    }


}

/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
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

    public DefinitionsList getDefList() {
        return defList;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

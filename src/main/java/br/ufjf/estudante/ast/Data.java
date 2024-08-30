package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class Data extends Definition {
    private final String id;
    private final Declarations declarations;

    public Data(String id, Declarations declarations, int line) {
        super(line);
        this.id = id;
        this.declarations = declarations;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

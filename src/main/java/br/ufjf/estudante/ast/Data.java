package br.ufjf.estudante.ast;

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
}

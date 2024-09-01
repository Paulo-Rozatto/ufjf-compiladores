package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class CommandRead extends Command {
    private final LValue lValue;

    public CommandRead(LValue value, int line) {
        super(line);
        this.lValue = value;
    }

    public LValue getLValue() {
        return lValue;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

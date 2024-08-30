package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class CommandRead extends Command {
    private final LValue value;

    public CommandRead(LValue value, int line) {
        super(line);
        this.value = value;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

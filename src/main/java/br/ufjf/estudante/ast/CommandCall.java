package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

import java.util.List;

public class CommandCall extends Command {
    private final ExpressionsList params;
    private final List<String> returnVars;

    public CommandCall(ExpressionsList params, List<String> returnVars, int line) {
        super(line);
        this.params = params;
        this.returnVars = returnVars;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

import java.util.List;

public class CommandCall extends Command {
    private final String id;
    private final ExpressionsList params;
    private final List<String> returnVars;

    public CommandCall(String id, ExpressionsList params, List<String> returnVars, int line) {
        super(line);
        this.id = id;
        this.params = params;
        this.returnVars = returnVars;
    }

    public String getId() {
        return id;
    }

    public ExpressionsList getParams() {
        return params;
    }

    public List<String> getReturnVars() {
        return returnVars;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

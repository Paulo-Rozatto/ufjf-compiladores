package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

import java.util.Map;
import java.util.Set;

public class Function extends Definition {
    private final String id;
    private final Params params;
    private final ReturnTypes returnTypes;
    private final CommandsList commandsList;

    public Function(String id, Params params, ReturnTypes returnTypes, CommandsList commandsList, int line) {
        super(line);
        this.id = id;
        this.params = params;
        this.returnTypes = returnTypes;
        this.commandsList = commandsList;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public Map<String, Type> getParams() {
        return params.getMap();
    }

    public ReturnTypes getReturnTypes() {
        return returnTypes;
    }

    public CommandsList getCommandsList() {
        return commandsList;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}
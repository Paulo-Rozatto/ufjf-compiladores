/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class CommandsList extends Command {
    private final List<Command> commands = new ArrayList<>();

    public CommandsList(int line) {
        super(line);
    }

    public void add(Command command) {
        commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

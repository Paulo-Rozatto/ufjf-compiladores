package br.ufjf.estudante.ast;

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
}

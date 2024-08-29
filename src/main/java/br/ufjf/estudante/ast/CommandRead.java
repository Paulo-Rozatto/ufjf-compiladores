package br.ufjf.estudante.ast;

public class CommandRead extends Command {
    private final LValue value;

    public CommandRead(LValue value, int line) {
        super(line);
        this.value = value;
    }
}

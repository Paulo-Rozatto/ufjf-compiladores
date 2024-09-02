/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class CommandIterate extends Command {
    private final Expression expression;
    private final Command command;

    public CommandIterate(Expression expression, Command command, int line) {
        super(line);
        this.expression = expression;
        this.command = command;
    }

    public Expression getExpression() {
        return expression;
    }

    public Command getCommand() {
        return command;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

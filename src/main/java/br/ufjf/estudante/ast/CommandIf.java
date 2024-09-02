/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package br.ufjf.estudante.ast;

import br.ufjf.estudante.visitor.Visitor;

public class CommandIf extends Command {
    private final Expression expression;
    private final Command then;
    private final Command otherwise;

    public CommandIf(Expression expression, Command then, int line) {
        super(line);
        this.expression = expression;
        this.then = then;
        this.otherwise = null;
    }

    public CommandIf(Expression expression, Command then, Command otherwise, int line) {
        super(line);
        this.expression = expression;
        this.then = then;
        this.otherwise = otherwise;
    }

    public Command getThen() {
        return then;
    }

    public Command getOtherwise() {
        return otherwise;
    }

    public Expression getExpression() {
        return expression;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}

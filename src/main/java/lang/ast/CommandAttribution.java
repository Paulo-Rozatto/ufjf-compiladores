/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package lang.ast;

import br.ufjf.estudante.visitor.Visitor;

public class CommandAttribution extends Command {
    private final LValue lValue;
    private final Expression expression;

    public CommandAttribution(LValue lValue, Expression expression, int line) {
        super(line);
        this.lValue = lValue;
        this.expression = expression;
    }

    public LValue getlValue() {
        return lValue;
    }

    public Expression getExpression() {
        return expression;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public int getColumn() {
        return -1;
    }
}

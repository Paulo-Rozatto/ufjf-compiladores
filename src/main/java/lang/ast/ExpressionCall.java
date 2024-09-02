/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package lang.ast;

import br.ufjf.estudante.util.Pair;
import br.ufjf.estudante.visitor.Visitor;
import br.ufjf.estudante.visitor.VisitorInterpreter;

import java.util.Map;

public class ExpressionCall extends Expression {
    private final String id;
    private final ExpressionsList params;
    private final Expression modifier;
    private Map<String, Pair<Type, Literal>> env;

    public ExpressionCall(String id, ExpressionsList params, Expression modifier, int line) {
        super(line);
        this.id = id;
        this.params = params;
        this.modifier = modifier;
    }

    public void accept(Visitor v) {
        v.visit(this);

        if (v.getClass() == VisitorInterpreter.class) {
            env = ((VisitorInterpreter) v).getEnv();
        } else {
            env = null;
        }
    }

    public String getId() {
        return id;
    }

    public ExpressionsList getParams() {
        return params;
    }

    public Expression getModifier() {
        return modifier;
    }

    @Override
    public Literal evaluate() {
        if (modifier == null || env == null) {
            return null;
        }

        if (!(modifier instanceof LiteralInt)) {
            throw new RuntimeException("Modificado invalido: " + modifier);
        }

        String returnIndex = modifier.evaluate().toString();
        return env.get(returnIndex).getSecond();
    }

    @Override
    public int getColumn() {
        return -1;
    }
}

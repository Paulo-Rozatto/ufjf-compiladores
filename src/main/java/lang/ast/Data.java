/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package lang.ast;

import br.ufjf.estudante.visitor.Visitor;

import java.util.Map;

public class Data extends Definition {
    private final String id;
    private final Declarations declarations;

    public Data(String id, Declarations declarations, int line) {
        super(line);
        this.id = id;
        this.declarations = declarations;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public Map<String, Type> getDeclarations() {
        return declarations.getInnerVariables();
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public int getColumn() {
        return -1;
    }
}

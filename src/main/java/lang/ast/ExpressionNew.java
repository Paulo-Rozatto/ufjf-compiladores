/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.visitor.Visitor;
import br.ufjf.estudante.visitor.VisitorInterpreter;
import java.lang.reflect.Array;
import java.util.Map;

public class ExpressionNew extends Expression {
  private final Type type;
  private final Expression exp;
  private Map<String, Definition> definitionMap = null;

  public ExpressionNew(Type type, int line) {
    super(line);
    this.type = type;
    this.exp = null;
  }

  public ExpressionNew(Type type, Expression exp, int line) {
    super(line);
    this.type = type;
    this.exp = exp;
  }

  public void accept(Visitor v) {
    if (v.getClass() == VisitorInterpreter.class) {
      definitionMap = ((VisitorInterpreter) v).getDefinitions();
    }
    if (exp != null) {
      exp.accept(v);
    }
    v.visit(this);
  }

  public Type getType() {
    return type;
  }

  public Expression getExp() {
    return exp;
  }

  @Override
  public Literal evaluate() {
    if (exp != null) {
      int size = ((LiteralInt) exp.evaluate()).getValue();

      Object array = Array.newInstance(type.getC(), size);

      for (int i = 1; i < type.getDimensions(); i++) {
        Object tempArray = Array.newInstance(array.getClass(), size);
        for (int j = 0; j < size; j++) {
          Array.set(tempArray, j, array);
        }
        array = tempArray;
      }

      return new LiteralArray(array, type.getDimensions(), size, lineNumber);
    }

    if (type.getClass() == TypePrimitive.class) {
      throw new RuntimeException("Instanciar primitivo é inválido");
    }

    if (definitionMap.isEmpty()) {
      throw new RuntimeException("Definições estão vazias!");
    }

    TypeCustom customType = (TypeCustom) type;
    Definition typeDefinition = definitionMap.get(customType.getId());

    if (typeDefinition == null) {
      throw new RuntimeException("Tipo '" + customType.getId() + "' inexistente");
    }

    if (typeDefinition.getClass() != Data.class) {
      throw new RuntimeException(customType.getId() + " não é um tipo");
    }

    return new LiteralCustom(
        customType.getId(), ((Data) typeDefinition).getDeclarations(), lineNumber);
  }

  @Override
  public int getColumn() {
    return -1;
  }
}

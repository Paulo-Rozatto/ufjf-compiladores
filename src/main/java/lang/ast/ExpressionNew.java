/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.util.VisitException;
import br.ufjf.estudante.visitor.Visitor;
import br.ufjf.estudante.visitor.VisitorInterpreter;
import com.google.common.collect.Multimap;
import java.lang.reflect.Array;
import java.util.Iterator;

public class ExpressionNew extends Expression {
  private final Type type;
  private final Expression exp;
  private Multimap<String, Definition> definitionMap = null;

  public ExpressionNew(Type type, int line) {
    super(line);
    this.type = type;
    this.exp = null;
  }

  public ExpressionNew(Type type, Expression exp, int line) {
    super(line);
    this.type = type;
    this.type.increaseDimensions();
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

      Object array = Array.newInstance(type.getLiteralClass(), size);

      for (int i = 1; i < type.getDimensions(); i++) {
        Object tempArray = Array.newInstance(array.getClass(), size);
        for (int j = 0; j < size; j++) {
          Array.set(tempArray, j, array);
        }
        array = tempArray;
      }

      return new LiteralArray(array, type, lineNumber);
    }

    if (type.getClass() != TypeCustom.class) {
      throw new VisitException("Instanciar primitivo é inválido", getLine());
    }

    if (definitionMap.isEmpty()) {
      throw new VisitException("Definições estão vazias!", getLine());
    }

    TypeCustom customType = (TypeCustom) type;
    Iterator<Definition> iterator = definitionMap.get(customType.getId()).iterator();

    if (!iterator.hasNext()) {
      throw new VisitException("Tipo '" + customType.getId() + "' inexistente", getLine());
    }

    Definition typeDefinition = iterator.next();
    if (typeDefinition.getClass() != Data.class) {
      throw new VisitException(customType.getId() + " não é um tipo", getLine());
    }

    return new LiteralCustom(
        customType.getId(), ((Data) typeDefinition).getDeclarations(), lineNumber);
  }

  @Override
  public int getColumn() {
    return -1;
  }
}

/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.singletons.SType;
import br.ufjf.estudante.visitor.Visitor;

public abstract class Type extends Node {
  private int dimensions;

  public Type(int line) {
    super(line);
    dimensions = 1;
  }

  public void increaseDimensions() {
    dimensions += 1;
  }

  public int getDimensions() {
    return dimensions;
  }

  public abstract Class<?> getLiteralClass();

  public void accept(Visitor v) {
    v.visit(this);
  }

  public abstract SType getSType();

  @Override
  public int getColumn() {
    return -1;
  }
}

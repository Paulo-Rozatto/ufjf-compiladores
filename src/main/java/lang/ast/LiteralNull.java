/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.visitor.Visitor;

public class LiteralNull extends Literal {

  public LiteralNull(int line) {
    super(line);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Type getType() {
    return new TypeNull(lineNumber);
  }

  @Override
  protected boolean checkArg(Literal arg) {
    return false;
  }

  @Override
  public Literal equals(Literal arg) {
    return new LiteralBool(arg.getClass() == LiteralNull.class, lineNumber);
  }

  @Override
  public Literal notEquals(Literal arg) {
    return new LiteralBool(arg.getClass() != LiteralNull.class, lineNumber);
  }

  @Override
  public String toString() {
    return "NULL";
  }
}

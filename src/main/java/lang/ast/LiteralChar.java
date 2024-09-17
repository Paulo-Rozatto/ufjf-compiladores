/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.util.VisitException;
import br.ufjf.estudante.visitor.Visitor;
import java.util.Objects;

public class LiteralChar extends Literal {
  private final String value;

  public LiteralChar(String value, int line) {
    super(line);
    this.value = value;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public String getValue() {
    return value;
  }

  public Type getType() {
    return new TypePrimitive(this.getClass(), lineNumber);
  }

  @Override
  protected boolean checkArg(Literal arg) {
    if (arg == null) {
      throw new VisitException("Argumento faltante!", getLine());
    }

    return arg.getClass() == LiteralChar.class;
  }

  @Override
  public Literal evaluate() {
    return this;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @Override
  public Literal equals(Literal arg) {
    if (checkArg(arg)) {
      boolean result = Objects.equals(value, ((LiteralChar) arg).getValue());
      return new LiteralBool(result, lineNumber);
    }

    return super.add(arg);
  }

  @Override
  public Literal notEquals(Literal arg) {
    if (checkArg(arg)) {
      boolean result = !Objects.equals(value, ((LiteralChar) arg).getValue());
      return new LiteralBool(result, lineNumber);
    }

    return super.add(arg);
  }

  @Override
  public int getColumn() {
    return -1;
  }
}

/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.util.VisitException;
import br.ufjf.estudante.visitor.Visitor;

public class LiteralBool extends Literal {
  private final Boolean value;

  public LiteralBool(Boolean value, int line) {
    super(line);
    this.value = value;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public Boolean getValue() {
    return value;
  }

  @Override
  public Literal evaluate() {
    return this;
  }

  @Override
  public Literal and(Literal arg) {
    if (arg.getClass() == LiteralBool.class) {
      boolean result = value && ((LiteralBool) arg).getValue();
      return new LiteralBool(result, lineNumber);
    }

    return super.add(arg);
  }

  //    @Override
  //    public Literal or(Literal arg) {
  //        if(arg.getClass() == LiteralBool.class) {
  //            boolean result = value || ((LiteralBool) arg).getValue();
  //            return new LiteralBool(result, lineNumber);
  //        }
  //
  //        return super.add(arg);
  //    }

  @Override
  public Literal not() {
    return new LiteralBool(!value, lineNumber);
  }

  @Override
  public Literal equals(Literal arg) {
    if (checkArg(arg)) {
      boolean result = value == ((LiteralBool) arg).getValue();
      return new LiteralBool(result, lineNumber);
    }

    return super.add(arg);
  }

  @Override
  public Literal notEquals(Literal arg) {
    if (checkArg(arg)) {
      boolean result = value != ((LiteralBool) arg).getValue();
      return new LiteralBool(result, lineNumber);
    }

    return super.add(arg);
  }

  public Type getType() {
    return new TypeBool(lineNumber);
  }

  @Override
  protected boolean checkArg(Literal arg) {
    if (arg == null) {
      throw new VisitException("Argumento faltante!", getLine());
    }

    return arg.getClass() == LiteralBool.class;
  }

  @Override
  public String toString() {
    return value.toString();
  }

  @Override
  public int getColumn() {
    return -1;
  }
}

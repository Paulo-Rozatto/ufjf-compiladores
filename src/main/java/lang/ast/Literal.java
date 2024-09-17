/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.util.VisitException;

public abstract class Literal extends Expression {
  public Literal(int line) {
    super(line);
  }

  @Override
  public Literal evaluate() {
    return new LiteralNull(getLine());
  }

  public abstract Type getType();

  protected abstract boolean checkArg(Literal arg);

  public Literal equals(Literal arg) {
    throw new VisitException("Operação '==' não suportada com argumento " + arg, getLine());
  }

  public Literal notEquals(Literal arg) {
    throw new VisitException("Operação '!=' não suportada com argumento " + arg, getLine());
  }

  public Literal smaller(Literal arg) {
    throw new VisitException("Operação '<' não suportada com argumento " + arg, getLine());
  }

  public Literal add(Literal arg) {
    throw new VisitException("Operação '+' não suportada com argumento " + arg, getLine());
  }

  public Literal sub(Literal arg) {
    throw new VisitException("Operação '-' não suportada com argumento " + arg, getLine());
  }

  public Literal mul(Literal arg) {
    throw new VisitException("Operação '*' não suportada com argumento " + arg, getLine());
  }

  public Literal div(Literal arg) {
    throw new VisitException("Operação '/' não suportada com argumento " + arg, getLine());
  }

  public Literal mod(Literal arg) {
    throw new VisitException("Operação '%' não suportada com argumento " + arg, getLine());
  }

  public Literal and(Literal arg) {
    throw new VisitException("Operação '&&' não suportada com argumento " + arg, getLine());
  }

  //    public Literal or(Literal arg) {
  //        throw new VisitException("Operação não suportada com argumento " + arg, getLine());
  //    }

  public Literal not() {
    throw new VisitException("Operação '!' não suportada", getLine());
  }

  @Override
  public int getColumn() {
    return -1;
  }
}

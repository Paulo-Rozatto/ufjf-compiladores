/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.util.VisitException;
import br.ufjf.estudante.visitor.Visitor;

public class ExpressionBoolean extends Expression {
  private final String op;
  private final Expression left;
  private final Expression right;

  public ExpressionBoolean(String op, Expression left, Expression right, int line) {
    super(line);
    this.op = op;
    this.left = left;
    this.right = right;
  }

  public void accept(Visitor v) {
    if (left != null) {
      left.accept(v);
    }

    if (right != null) {
      right.accept(v);
    }
    v.visit(this);
  }

  @Override
  public Literal evaluate() {

    return switch (op) {
      case "!" -> right.evaluate().not();
      case "&&" -> left.evaluate().and(right.evaluate());
      case "==" -> left.evaluate().equals(right.evaluate());
      case "!=" -> left.evaluate().notEquals(right.evaluate());
      //            case "||" -> left.evaluate().or(right.evaluate());
      default -> throw new VisitException("Operador booleano desconhecido: " + op, getLine());
    };
  }

  @Override
  public int getColumn() {
    return -1;
  }

  public String getOp() {
    return op;
  }

  public Expression getLeft() {
    return left;
  }

  public Expression getRight() {
    return right;
  }
}

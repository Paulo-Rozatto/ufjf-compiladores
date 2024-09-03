/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.visitor.Visitor;

public class ExpressionArithmetic extends Expression {
  private final String op;
  private final Expression left;
  private final Expression right;

  public ExpressionArithmetic(String op, Expression left, Expression right, int line) {
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

  public String getOp() {
    return op;
  }

  public Expression getLeft() {
    return left;
  }

  public Expression getRight() {
    return right;
  }

  @Override
  public Literal evaluate() {
    return switch (op) {
      case "+" -> left.evaluate().add(right.evaluate());
      case "-" -> {
        if (left == null) {
          Literal rightValue = right.evaluate();
          Literal zero =
              rightValue.getClass() == LiteralInt.class
                  ? new LiteralInt(0, lineNumber)
                  : new LiteralFloat(0, lineNumber);
          yield zero.sub(rightValue);
        }
        yield left.evaluate().sub(right.evaluate());
      }
      case "*" -> left.evaluate().mul(right.evaluate());
      case "/" -> left.evaluate().div(right.evaluate());
      case "%" -> left.evaluate().mod(right.evaluate());
      case "<" -> left.evaluate().smaller(right.evaluate());
      case "==" -> left.evaluate().equals(right.evaluate());
      case "!=" -> left.evaluate().notEquals(right.evaluate());

      default -> throw new RuntimeException("Operador aritimético desconhecido: " + op);
    };
  }

  @Override
  public int getColumn() {
    return -1;
  }
}

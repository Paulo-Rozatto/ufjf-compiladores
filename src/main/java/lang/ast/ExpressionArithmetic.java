/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.util.VisitException;
import br.ufjf.estudante.visitor.Visitor;

public class ExpressionArithmetic extends Expression {
  private final String op;
  private final Expression left;
  private final Expression right;
  private Literal leftValue;
  private Literal rightValue;

  public ExpressionArithmetic(String op, Expression left, Expression right, int line) {
    super(line);
    this.op = op;
    this.left = left;
    this.right = right;
  }

  public void accept(Visitor v) {
    Literal leftResult = null, rightResult = null;
    if (left != null) {
      left.accept(v);
      leftResult = left.evaluate();
    }

    if (right != null) {
      right.accept(v);
      rightResult = right.evaluate();
    }

    leftValue = leftResult;
    rightValue = rightResult;
    v.visit(this);
  }

  @Override
  public Literal evaluate() {
    try {
      return switch (op) {
        case "+" -> leftValue.add(rightValue);
        case "-" -> {
          if (left == null) {
            Literal zero =
                rightValue.getClass() == LiteralInt.class
                    ? new LiteralInt(0, lineNumber)
                    : new LiteralFloat(0, lineNumber);
            yield zero.sub(rightValue);
          }
          yield leftValue.sub(rightValue);
        }
        case "*" -> leftValue.mul(rightValue);
        case "/" -> leftValue.div(rightValue);
        case "%" -> leftValue.mod(rightValue);
        case "<" -> leftValue.smaller(rightValue);
        case "==" -> leftValue.equals(rightValue);
        case "!=" -> leftValue.notEquals(rightValue);

        default -> throw new VisitException("Operador aritimético desconhecido: " + op, getLine());
      };
    } catch (NullPointerException e) {
      throw new VisitException("Null pointer exception!", getLine());
    }
  }

  @Override
  public int getColumn() {
    return -1;
  }
}

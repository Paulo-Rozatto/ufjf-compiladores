/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.visitor.Visitor;

public abstract class Node extends SuperNode {
  protected int lineNumber;

  public Node(int line) {
    lineNumber = line;
  }

  public int getLine() {
    return lineNumber;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}

/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.visitor.Visitor;
import java.util.HashMap;
import java.util.Map;

public class Params extends Node {
  private final Map<String, Type> params = new HashMap<>();

  public Params(int line) {
    super(line);
  }

  public void add(String id, Type type) {
    params.put(id, type);
  }

  public Map<String, Type> getMap() {
    return params;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  @Override
  public int getColumn() {
    return -1;
  }
}

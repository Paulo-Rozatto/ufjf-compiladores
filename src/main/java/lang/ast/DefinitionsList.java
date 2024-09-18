/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.visitor.Visitor;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.HashMap;
import java.util.Map;

public class DefinitionsList extends Node {
  private final Multimap<String, Definition> definitionMap = ArrayListMultimap.create();

  public DefinitionsList(int line) {
    super(line);
  }

  public DefinitionsList(Definition d, int line) {
    super(line);
    definitionMap.put(d.getId(), d);
  }

  public void add(Definition d) {
    definitionMap.put(d.getId(), d);
  }

  public Multimap<String, Definition> getDefinitionMap() {
    return definitionMap;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  @Override
  public int getColumn() {
    return -1;
  }
}

/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package lang.ast;

import br.ufjf.estudante.singletons.SArray;
import br.ufjf.estudante.singletons.SCustom;
import br.ufjf.estudante.singletons.SType;
import br.ufjf.estudante.visitor.Visitor;
import java.util.HashMap;
import java.util.Map;

public class TypeCustom extends Type {
  private static final Map<String, TypeCustom> runtimeTypes = new HashMap<>();
  private final String id;

  public TypeCustom(String id, int line) {
    super(line);
    this.id = id;
  }

  public static TypeCustom getType(String id) {
    TypeCustom type = runtimeTypes.get(id);
    if (type == null) {
      type = new TypeCustom(id, 0);
      runtimeTypes.put(id, type);
    }
    return type;
  }

  @Override
  public Class<LiteralCustom> getLiteralClass() {
    return LiteralCustom.class;
  }

  public String getId() {
    return id;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  @Override
  public SType getSType() {
    SCustom custom = new SCustom(id);

    if (getDimensions() > 1) {
      SArray sType = new SArray(custom);
      for (int i = 0; i < getDimensions(); i++) {
        sType = new SArray(sType);
      }
    }

    return custom;
  }
}

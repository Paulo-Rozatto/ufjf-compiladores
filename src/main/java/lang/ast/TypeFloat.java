package lang.ast;

import br.ufjf.estudante.singletons.SFloat;
import br.ufjf.estudante.singletons.SType;

public class TypeFloat extends Type {
  public TypeFloat(int line) {
    super(line);
  }

  @Override
  public Class<?> getLiteralClass() {
    return LiteralFloat.class;
  }

  @Override
  public SType getSType() {
    return SFloat.newSFloat();
  }
}

package lang.ast;

import br.ufjf.estudante.singletons.SArray;
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
    SType type = SFloat.newSFloat();

    for (int i = 1; i < getDimensions(); i++) {
      type = new SArray(type);
    }

    return type;
  }
}

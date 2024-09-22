package lang.ast;

import br.ufjf.estudante.types.SArray;
import br.ufjf.estudante.types.SFloat;
import br.ufjf.estudante.types.SType;

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

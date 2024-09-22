package lang.ast;

import br.ufjf.estudante.types.SArray;
import br.ufjf.estudante.types.SBoolean;
import br.ufjf.estudante.types.SType;

public class TypeBool extends Type {
  public TypeBool(int line) {
    super(line);
  }

  @Override
  public Class<?> getLiteralClass() {
    return LiteralChar.class;
  }

  @Override
  public SType getSType() {
    SType type = SBoolean.newSBoolean();

    for (int i = 1; i < getDimensions(); i++) {
      type = new SArray(type);
    }

    return type;
  }
}

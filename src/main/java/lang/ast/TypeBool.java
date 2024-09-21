package lang.ast;

import br.ufjf.estudante.singletons.SArray;
import br.ufjf.estudante.singletons.SBoolean;
import br.ufjf.estudante.singletons.SType;

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

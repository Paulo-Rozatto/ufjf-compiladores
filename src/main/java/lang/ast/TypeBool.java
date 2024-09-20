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
    if (getDimensions() > 1) {
      SArray sType = new SArray(SBoolean.newSBoolean());
      for (int i = 0; i < getDimensions(); i++) {
        sType = new SArray(sType);
      }
    }

    return SBoolean.newSBoolean();
  }
}

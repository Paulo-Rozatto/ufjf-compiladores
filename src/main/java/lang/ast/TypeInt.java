package lang.ast;

import br.ufjf.estudante.singletons.SArray;
import br.ufjf.estudante.singletons.SInt;
import br.ufjf.estudante.singletons.SType;

public class TypeInt extends Type {
  public TypeInt(int line) {
    super(line);
  }

  @Override
  public Class<?> getLiteralClass() {
    return LiteralInt.class;
  }

  @Override
  public SType getSType() {
    SType type = SInt.newSInt();

    for (int i = 1; i < getDimensions(); i++) {
      type = new SArray(type);
    }

    return type;
  }
}

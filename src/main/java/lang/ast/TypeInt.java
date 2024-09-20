package lang.ast;

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
    return SInt.newSInt();
  }
}

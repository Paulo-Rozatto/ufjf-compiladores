package lang.ast;

import br.ufjf.estudante.singletons.SArray;
import br.ufjf.estudante.singletons.SType;

public class TypeArray extends Type {
  public TypeArray(int line) {
    super(line);
  }

  @Override
  public Class<?> getLiteralClass() {
    return LiteralArray.class;
  }

  @Override
  public SType getSType() {
    return null;
  }
}

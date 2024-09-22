package lang.ast;

import br.ufjf.estudante.types.SType;

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

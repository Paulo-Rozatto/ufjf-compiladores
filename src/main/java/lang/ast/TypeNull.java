package lang.ast;

import br.ufjf.estudante.singletons.SNull;
import br.ufjf.estudante.singletons.SType;

public class TypeNull extends Type {
  public TypeNull(int line) {
    super(line);
  }

  @Override
  public Class<?> getLiteralClass() {
    return LiteralNull.class;
  }

  @Override
  public SType getSType() {
    return SNull.newSNull();
  }
}

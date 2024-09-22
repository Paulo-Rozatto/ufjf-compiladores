package lang.ast;

import br.ufjf.estudante.types.SArray;
import br.ufjf.estudante.types.SChar;
import br.ufjf.estudante.types.SType;

public class TypeChar extends Type {
  public TypeChar(int line) {
    super(line);
  }

  @Override
  public Class<?> getLiteralClass() {
    return LiteralChar.class;
  }

  @Override
  public SType getSType() {
    SType type = SChar.newSChar();

    for (int i = 1; i < getDimensions(); i++) {
      type = new SArray(type);
    }

    return type;
  }
}

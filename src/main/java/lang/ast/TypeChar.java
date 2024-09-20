package lang.ast;

import br.ufjf.estudante.singletons.SArray;
import br.ufjf.estudante.singletons.SChar;
import br.ufjf.estudante.singletons.SType;

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
    if (getDimensions() > 1) {
      SArray sType = new SArray(SChar.newSChar());
      for (int i = 0; i < getDimensions(); i++) {
        sType = new SArray(sType);
      }
    }

    return SChar.newSChar();
  }
}

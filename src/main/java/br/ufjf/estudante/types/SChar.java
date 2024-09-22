package br.ufjf.estudante.types;

public class SChar extends SType {
  private static final SChar sChar = new SChar();

  private SChar() {}

  public static SChar newSChar() {
    return sChar;
  }

  @Override
  public boolean match(SType value) {
    if (value instanceof SOr) {
      return value.match(SChar.newSChar());
    }
    return (value instanceof SChar) || (value instanceof SError);
  }

  @Override
  public String toString() {
    return "Char";
  }
}

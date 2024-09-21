package br.ufjf.estudante.singletons;

public class SChar extends SType {
  private static final SChar sChar = new SChar();

  private SChar() {}

  public static SChar newSChar() {
    return sChar;
  }

  @Override
  public boolean match(SType value) {
    return (value instanceof SChar) || (value instanceof SError);
  }

  @Override
  public String toString() {
    return "Char";
  }
}
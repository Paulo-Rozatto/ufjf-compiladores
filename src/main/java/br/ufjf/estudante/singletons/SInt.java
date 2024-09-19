package br.ufjf.estudante.singletons;

public class SInt extends SType {
  private static final SInt sInt = new SInt();

  private SInt() {}

  public static SInt newSInt() {
    return sInt;
  }

  @Override
  public boolean match(SType value) {
    return (value instanceof SInt) || (value instanceof SError);
  }

  @Override
  public String toString() {
    return "Int";
  }
}

package br.ufjf.estudante.singletons;

public class SNull extends SType {
  private static final SNull sNull = new SNull();

  private SNull() {}

  public static SNull newSNull() {
    return sNull;
  }

  @Override
  public boolean match(SType value) {
    return (value instanceof SNull) || (value instanceof SError);
  }

  @Override
  public String toString() {
    return "Null";
  }
}

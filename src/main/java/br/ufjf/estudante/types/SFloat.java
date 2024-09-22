package br.ufjf.estudante.types;

public class SFloat extends SType {
  private static final SFloat sFloat = new SFloat();

  private SFloat() {}

  public static SFloat newSFloat() {
    return sFloat;
  }

  @Override
  public boolean match(SType value) {
    if (value instanceof SOr) {
      return value.match(SFloat.newSFloat());
    }
    return (value instanceof SFloat) || (value instanceof SError);
  }

  @Override
  public String toString() {
    return "Float";
  }
}

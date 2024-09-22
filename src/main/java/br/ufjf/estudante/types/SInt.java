package br.ufjf.estudante.types;

public class SInt extends SType {
  private static final SInt sInt = new SInt();

  private Integer value = null;

  public SInt(int value) {
    this.value = value;
  }

  private SInt() {}

  public Integer getValue() {
    return value;
  }

  public static SInt newSInt() {
    return sInt;
  }

  @Override
  public boolean match(SType value) {
    if (value instanceof SOr) {
      return value.match(SInt.newSInt());
    }
    return (value instanceof SInt) || (value instanceof SError);
  }

  @Override
  public String toString() {
    return "Int";
  }
}

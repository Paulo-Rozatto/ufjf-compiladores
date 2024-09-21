package br.ufjf.estudante.singletons;

public class SBoolean extends SType {
  private static final SBoolean sBoolean = new SBoolean();

  private SBoolean() {}

  public static SBoolean newSBoolean() {
    return sBoolean;
  }

  @Override
  public boolean match(SType value) {
    if (value instanceof SOr) {
      return value.match(SBoolean.newSBoolean());
    }
    return (value instanceof SBoolean) || (value instanceof SError);
  }

  @Override
  public String toString() {
    return "Boolean";
  }
}

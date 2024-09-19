package br.ufjf.estudante.singletons;

public class SArray extends SType {
  private final SType type;

  public SArray(SType type) {
    this.type = type;
  }

  public SType getType() {
    return type;
  }

  @Override
  public boolean match(SType value) {
    return (value instanceof SError)
        || (value instanceof SArray) && type.match(((SArray) value).getType());
  }

  @Override
  public String toString() {
    return type + "[]";
  }
}

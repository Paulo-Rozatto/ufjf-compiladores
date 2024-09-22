package br.ufjf.estudante.types;

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
    if (value instanceof SOr) {
      return value.match(type);
    }

    return (value instanceof SError)
        || (value instanceof SArray) && type.match(((SArray) value).getType());
  }

  @Override
  public String toString() {
    return type + "[]";
  }
}

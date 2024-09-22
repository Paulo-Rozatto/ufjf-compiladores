package br.ufjf.estudante.types;

public class SFunction extends SType {
  private final SType[] argTypes;
  private final SType[] returnTypes;

  public SFunction(SType[] argTypes, SType[] returnTypes) {
    this.argTypes = argTypes;
    this.returnTypes = returnTypes;
  }

  public SFunction(SType[] argTypes) {
    this.argTypes = argTypes;
    this.returnTypes = null;
  }

  public SType[] getArgTypes() {
    return argTypes;
  }

  public int getArgsLen() {
    return argTypes.length;
  }

  public SType[] getReturnTypes() {
    return returnTypes;
  }

  public SType getReturn(int i) {
    return returnTypes == null || i > (returnTypes.length - 1) ? null : returnTypes[i];
  }

  public int getReturnLen() {
    return returnTypes == null ? 0 : returnTypes.length;
  }

  @Override
  public boolean match(SType value) {
    if (!(value instanceof SFunction func)) {
      return false;
    }

    if (this.argTypes == null && func.getArgTypes() == null) {
      return true;
    }

    if (this.argTypes == null && func.getArgTypes() != null
        || this.argTypes != null && func.getArgTypes() == null) {
      return false;
    }

    if (func.getArgTypes().length != this.argTypes.length) {
      return false;
    }

    for (int i = 0; i < this.argTypes.length; i++) {
      if (!this.argTypes[i].match(func.argTypes[i])) {
        return false;
      }
    }

    return true;
  }
}

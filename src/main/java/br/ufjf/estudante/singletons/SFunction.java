package br.ufjf.estudante.singletons;

public class SFunction extends SType {
  private final SType[] argTypes;
  private final SType[] returnTypes; // todo: remover se nao usar depois

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

  @Override
  public boolean match(SType value) {
    if (!(value instanceof SFunction func)) {
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
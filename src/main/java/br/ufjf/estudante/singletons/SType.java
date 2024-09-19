package br.ufjf.estudante.singletons;

public abstract class SType {
  public abstract boolean match(SType value);

  @Override
  public String toString() {
    return "Abstrato";
  }
}

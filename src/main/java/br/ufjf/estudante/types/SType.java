package br.ufjf.estudante.types;

public abstract class SType {
  public abstract boolean match(SType value);

  @Override
  public String toString() {
    return "Abstrato";
  }
}

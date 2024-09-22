package br.ufjf.estudante.types;

public class SError extends SType {
  private static final SError sError = new SError();

  private SError() {}

  public static SType newSError() {
    return sError;
  }

  @Override
  public boolean match(SType value) {
    return true;
  }

  @Override
  public String toString() {
    return "Erro de tipo.";
  }
}

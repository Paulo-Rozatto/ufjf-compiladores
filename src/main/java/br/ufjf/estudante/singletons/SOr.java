package br.ufjf.estudante.singletons;

public class SOr extends SType {
  private final SType[] possibleTypes;

  public SOr(SType[] possibleTypes) {
    this.possibleTypes = possibleTypes;
  }

  @Override
  public boolean match(SType value) {
    for (SType possibleType : possibleTypes) {
      if (possibleType.match(value)) {
        return true;
      }
    }
    return false;
  }
}

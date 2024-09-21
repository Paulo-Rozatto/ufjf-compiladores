package br.ufjf.estudante.singletons;

public class SMulti extends SType {
  private final SType[] possibleTypes;

  public SMulti(SType[] possibleTypes) {
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

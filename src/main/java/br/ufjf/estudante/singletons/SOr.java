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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("OR(");

    for (int i = 0; i < possibleTypes.length; i++) {
      sb.append(possibleTypes[i].toString());
      if (i < possibleTypes.length - 1) {
        sb.append(", ");
      }
    }

    sb.append(")");
    return sb.toString();
  }
}

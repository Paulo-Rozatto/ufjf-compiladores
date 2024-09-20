package br.ufjf.estudante.singletons;

public abstract class SType {
  //  public static SType literalToType(Literal literal) {
  //    if (literal.getClass() == LiteralInt.class) {
  //      return SInt.newSInt();
  //    } else if (literal.getClass() == LiteralFloat.class) {
  //      return SFloat.newSFloat();
  //    } else if (literal.getClass() == LiteralChar.class) {
  //      return SChar.newSChar();
  //    } else if (literal.getClass() == LiteralBool.class) {
  //      return SBoolean.newSBoolean();
  //    } else if (literal.getClass() == LiteralNull.class) {
  //      return SNull.newSNull();
  //    } else if (literal.getClass() == LiteralArray.class) {
  ////      return new SArray(TypeToSType(type.));
  //    }
  //    return null
  //  }
  //
  //  public static SType TypeToSType(Type type) {
  //    if (type.getLiteralClass() == LiteralInt.class) {
  //      return SInt.newSInt();
  //    } else if (type.getLiteralClass() == LiteralFloat.class) {
  //      return SFloat.newSFloat();
  //    } else if (type.getLiteralClass() == LiteralChar.class) {
  //      return SChar.newSChar();
  //    } else if (type.getLiteralClass() == LiteralBool.class) {
  //      return SBoolean.newSBoolean();
  //    } else if (type.getLiteralClass() == LiteralNull.class) {
  //      return SNull.newSNull();
  //    } else if (type.getLiteralClass() == LiteralArray.class) {
  //      return new SArray(literalToType(type.getLiteralClass().));
  //    }
  //
  //    return null;
  //  }

  public abstract boolean match(SType value);

  @Override
  public String toString() {
    return "Abstrato";
  }
}

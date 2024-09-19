package br.ufjf.estudante.singletons;

public class SFloat extends SType {
    private static final SFloat sFloat = new SFloat();

    private SFloat() {}

    public static SFloat newSFloat() {
        return sFloat;
    }

    @Override
    public boolean match(SType value) {
        return (value instanceof SFloat) || (value instanceof SError);
    }

    @Override
    public String toString() {
        return "Float";
    }
}

package br.ufjf.estudante.ast;

public class LiteralArray extends Literal {
    private final Object array;
    private final int dimensions;
    private final int lastSize;

    public LiteralArray(Object array, int dimensions, int lastSize, int line) {
        super(line);
        this.array = array;
        this.dimensions = dimensions;
        this.lastSize = lastSize;
    }

    public Object getArray() {
        return array;
    }

    public int getDimensions() {
        return dimensions;
    }

    public int getLastSize() {
        return lastSize;
    }

    @Override
    public Type getType() {
        return new TypePrimitive(this.getClass(), lineNumber);
    }
}

package br.ufjf.estudante.ast;

public abstract class Literal extends Expression {
    public Literal(int line) {
        super(line);
    }

    @Override
    public Literal evaluate() {
        return null;
    }

    abstract public Type getType();

    public Literal equals(Literal arg) {
        throw new RuntimeException("Operação não suportada");
    }

    public Literal notEquals(Literal arg) {
        throw new RuntimeException("Operação não suportada");
    }

    public Literal smaller(Literal arg) {
        throw new RuntimeException("Operação não suportada");
    }

    public Literal add(Literal arg) {
        throw new RuntimeException("Operação não suportada");
    }

    public Literal sub(Literal arg) {
        throw new RuntimeException("Operação não suportada");
    }

    public Literal mul(Literal arg) {
        throw new RuntimeException("Operação não suportada");
    }

    public Literal div(Literal arg) {
        throw new RuntimeException("Operação não suportada");
    }

    public Literal mod(Literal arg) {
        throw new RuntimeException("Operação não suportada");
    }

    public Literal and(Literal arg) {
        throw new RuntimeException("Operação não suportada");
    }

//    public Literal or(Literal arg) {
//        throw new RuntimeException("Operação não suportada");
//    }

    public Literal not() {
        throw new RuntimeException("Operação não suportada");
    }

}

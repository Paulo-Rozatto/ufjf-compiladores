package br.ufjf.estudante.ast;


abstract public class Node {
    protected int lineNumber;

    public Node(int line) {
        lineNumber = line;
    }

    public int getLine() {
        return lineNumber;
    }
}
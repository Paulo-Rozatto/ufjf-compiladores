package br.ufjf.estudante.tokens;

public record Token(TokenType type, int row, int col, Object value) {
}

/*
package br.ufjf.estudante.tokens;

public class Token {
    public TokenType type;
    public String lexeme;
    public Object info;
    public int row;
    public int col;

    public Token(TokenType type, int row, int col) {
        this.type = type;
        this.row = row;
        this.col = col;
    }

    public Token(TokenType type, String lexeme, Object info, int row, int col) {
        this.type = type;
        this.lexeme = lexeme;
        this.info = info;
        this.row = row;
        this.col = col;
    }
}

 */
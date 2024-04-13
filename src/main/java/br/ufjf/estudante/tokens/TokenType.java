package br.ufjf.estudante.tokens;

public enum TokenType {
    KEYWORD,
    IDENTIFIER,
    TYPE,

    // PRIMITIVES
    INT("INT"),
    FLOAT("FLOAT"),
    CHAR("CHAR"),
    BOOL("BOOL"),

    // LITERALS
    LIT_INT,
    LIT_FLOAT,
    LIT_CHAR,
    LIT_BOOL,
    LIT_NULL,

    // BRACES
    ROUND_L("("),
    ROUND_R(")"),
    SQUARE_L("["),
    SQUARE_R("]"),
    CURLY_L("{"),
    CURLY_R("}"),

    // SEPARATORS
    COLON(":"),
    SEMICOLON(";"),
    DOUBLE_COLON("::"),
    DOT("."),
    COMMA(","),

    // LOGICAL OPERATORS
    GREATER(">"),
    SMALLER("<"),
    EQUALS("=="),
    NOT_EQUALS("!="),
    AND("&&"),
    NOT("!"),

    // MATH OPERATORS
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    MOD("%");

    public final String label;

    TokenType(String label) {
        this.label = label;
    }

    TokenType() {
        this.label = "";
    }
}

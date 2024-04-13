package br.ufjf.estudante.tokens;

public enum TokenType {
    KEYWORD,
    IDENTIFIER("ID"),

    // KEYWORDS
    IF("IF"),
    THEN("THEN"),
    ELSE("ELSE"),
    PRINT("PRINT"),
    READ("READ"),
    ITERATE("ITERATE"),
    RETURN("RETURN"),

    // PRIMITIVES
    INT("INT"),
    FLOAT("FLOAT"),
    CHAR("CHAR"),
    BOOL("BOOL"),

    // LITERALS
    LIT_INT("INT"),
    LIT_FLOAT("FLOAT"),
    LIT_CHAR("CHAR"),
    LIT_BOOL("BOOL"),
    LIT_NULL("NULL"),

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

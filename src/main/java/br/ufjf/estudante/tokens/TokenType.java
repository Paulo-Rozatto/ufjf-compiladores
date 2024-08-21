/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */

package br.ufjf.estudante.tokens;

public enum TokenType {
    IDENTIFIER("ID"),

    // KEYWORDS
    IF("IF"),
    THEN("THEN"),
    ELSE("ELSE"),
    PRINT("PRINT"),
    READ("READ"),
    ITERATE("ITERATE"),
    RETURN("RETURN"),
    NEW("NEW"),
    DATA("DATA"),

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
    LIT_TRUE("TRUE"),
    LIT_FALSE("FALSE"),

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
    ATTRIBUTION("="),
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    MOD("%"),


    // EOF
    EOF("<<EOF>>");

    public final String label;

    TokenType(String label) {
        this.label = label;
    }
}

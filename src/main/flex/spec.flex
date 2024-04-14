/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */


package de.jflex;

import java.rmi.UnexpectedException;
import java.util.Optional;

import br.ufjf.estudante.tokens.TokenType;
import br.ufjf.estudante.tokens.Token;

%%

%public
%class Lexer
%unicode
%line
%column
%type Token
%function nextToken

%{
    private int tokensSize;

    public int getTokensSize() {
        return  tokensSize;
    };

    private Token token(TokenType type) {
        tokensSize += 1;
        return new Token(type, yyline+1, yycolumn+1, Optional.empty());
    }

    private Token token(TokenType type, Object value) {
        tokensSize += 1;
        return new Token(type, yyline+1, yycolumn+1, Optional.of(value));
    }
%}

%init{
    tokensSize = 0;
%init}

LineTerminator	= \r|\n|\r\n
InputCharacter	= [^\r\n]
WhiteSpace      = {LineTerminator}|[ \t\f]
Identifier      = [:lowercase:][:jletterdigit:]*
PrimitiveLike   = [:uppercase:][:jletterdigit:]*
LiteralInt      = [0-9]+
LiteralFloat    = [0-9]*\.[0-9]+
LiteralChar     = '([:jletter:]|\\n|\\t|\\b|\\r|\\\\)'
Comment         = "--" {InputCharacter}* {LineTerminator}? // provavelmente o ? serve caso o comentario esteja no final do arquivo

%state MULTI_COMMENT

%%

<YYINITIAL>{
    // Nothing to do
    {WhiteSpace} {}
    {Comment}    {}
    "{-"         { yybegin(MULTI_COMMENT); }

    // Keywords
    "if"       { return token(TokenType.IF); }
    "then"     { return token(TokenType.THEN); }
    "else"     { return token(TokenType.ELSE); }
    "print"    { return token(TokenType.PRINT); }
    "read"     { return token(TokenType.READ); }
    "iterate"  { return token(TokenType.ITERATE); }
    "return"   { return token(TokenType.RETURN); }

    // Primitives
    "Int"           { return token(TokenType.INT); }
    "Float"         { return token(TokenType.FLOAT); }
    "Char"          { return token(TokenType.CHAR); }
    "Bool"          { return token(TokenType.BOOL); }
    {PrimitiveLike} { throw new UnexpectedException( String.format("%d:%d %s is not a valid primitive.", yyline + 1, yycolumn + 1, yytext()));    }

    // Literals
    {LiteralInt}     { return token(TokenType.LIT_INT, Integer.parseInt(yytext())); }
    {LiteralFloat}   { return token(TokenType.LIT_FLOAT, Float.parseFloat(yytext())); }
    {LiteralChar}    { return token(TokenType.LIT_CHAR, yytext()); }
    "true"           { return token(TokenType.LIT_BOOL, true); }
    "false"          { return token(TokenType.LIT_BOOL, false); }
    "null"           { return token(TokenType.LIT_NULL); }

    // BRACES
    "(" { return token(TokenType.ROUND_L); }
    ")" { return token(TokenType.ROUND_R); }
    "[" { return token(TokenType.SQUARE_L); }
    "]" { return token(TokenType.SQUARE_R); }
    "{" { return token(TokenType.CURLY_L); }
    "}" { return token(TokenType.CURLY_R); }

    // SEPARATORS
    "::" { return token(TokenType.DOUBLE_COLON); }
    ":"  { return token(TokenType.COLON); }
    ";"  { return token(TokenType.SEMICOLON); }
    "."  { return token(TokenType.DOT); }
    ","  { return token(TokenType.COMMA); }

    // LOGICAL OPERATORS
    ">"  { return token(TokenType.GREATER); }
    "<"  { return token(TokenType.SMALLER); }
    "==" { return token(TokenType.EQUALS); }
    "!=" { return token(TokenType.NOT_EQUALS); }
    "&&" { return token(TokenType.AND); }
    "!"  { return token(TokenType.NOT); }

    // MATH OPERATORS
    "=" { return token(TokenType.ATTRIBUTION); }
    "+" { return token(TokenType.ADDITION); }
    "-" { return token(TokenType.SUBTRACTION); }
    "*" { return token(TokenType.MULTIPLICATION); }
    "/" { return token(TokenType.DIVISION); }
    "%" { return token(TokenType.MOD); }

    // Identifier
    {Identifier}  { return token(TokenType.IDENTIFIER, yytext()); }

}

<MULTI_COMMENT>{
   "-}"     { yybegin(YYINITIAL); }
   [^"-}"]  {}
}

// ERROR
[^] { throw new UnexpectedException( String.format("%d:%d %s is not a valid character.", yyline + 1, yycolumn + 1, yytext())); }

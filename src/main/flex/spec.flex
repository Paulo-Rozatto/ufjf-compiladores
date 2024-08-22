/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */


package de.jflex;

import java_cup.runtime.*;
import java.rmi.UnexpectedException;
import java.util.Optional;

import br.ufjf.estudante.tokens.TokenType;
import br.ufjf.estudante.tokens.Token;

import lang.Symbols;
//Symbols.$1, TokenType.$1

%%

%public
%final
%class Lexer
%unicode
%line
%column
%type Symbol
%cup
%function next_token
%eofval{
  return token(Symbols.EOF, TokenType.EOF);
%eofval}


%{
    private int tokensSize;

    public int getTokensSize() {
        return  tokensSize;
    };

    private Symbol token(int type, TokenType tokenType) {
        tokensSize += 1;
//        Token tk = new Token(tokenType, Optional.empty());
        return  new Symbol(type, yyline+1, yycolumn+1);
    }

    private Symbol token(int type, TokenType tokenType, Object value) {
        tokensSize += 1;
//        Token tk = new Token(tokenType, Optional.of(value));
        return new Symbol(type, yyline+1, yycolumn+1, value);
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
LiteralChar     = '([:jletter:]|\\n|\\t|\\b|\\r|\\\\|\\')'
Comment         = "--" {InputCharacter}* {LineTerminator}? // provavelmente o ? serve caso o comentario esteja no final do arquivo

%state MULTI_COMMENT

%%

<YYINITIAL>{
    // Nothing to do
    {WhiteSpace} {}
    {Comment}    {}
    "{-"         { yybegin(MULTI_COMMENT); }

    // Keywords
    "if"       { return token(Symbols.IF, TokenType.IF); }
    "then"     { return token(Symbols.THEN, TokenType.THEN); }
    "else"     { return token(Symbols.ELSE, TokenType.ELSE); }
    "print"    { return token(Symbols.PRINT, TokenType.PRINT); }
    "read"     { return token(Symbols.READ, TokenType.READ); }
    "iterate"  { return token(Symbols.ITERATE, TokenType.ITERATE); }
    "return"   { return token(Symbols.RETURN, TokenType.RETURN); }
    "new"      { return token(Symbols.NEW, TokenType.NEW); }
    "data"     { return token(Symbols.DATA, TokenType.DATA); }

    // Primitives
    "Int"           { return token(Symbols.INT, TokenType.INT); }
    "Float"         { return token(Symbols.FLOAT, TokenType.FLOAT); }
    "Char"          { return token(Symbols.CHAR, TokenType.CHAR); }
    "Bool"          { return token(Symbols.BOOL, TokenType.BOOL); }
    {PrimitiveLike} { throw new UnexpectedException( String.format("%d:%d %s is not a valid primitive.", yyline + 1, yycolumn + 1, yytext()));    }

    // Literals
    {LiteralInt}     { return token(Symbols.LIT_INT, TokenType.LIT_INT, Integer.parseInt(yytext())); }
    {LiteralFloat}   { return token(Symbols.LIT_FLOAT, TokenType.LIT_FLOAT, Float.parseFloat(yytext())); }
    {LiteralChar}    { return token(Symbols.LIT_CHAR, TokenType.LIT_CHAR); }
    "true"           { return token(Symbols.LIT_TRUE, TokenType.LIT_BOOL); }
    "false"          { return token(Symbols.LIT_FALSE, TokenType.LIT_BOOL); }
    "null"           { return token(Symbols.LIT_NULL, TokenType.LIT_NULL); }

    // BRACES
    "(" { return token(Symbols.ROUND_L, TokenType.ROUND_L); }
    ")" { return token(Symbols.ROUND_R, TokenType.ROUND_R); }
    "[" { return token(Symbols.SQUARE_L, TokenType.SQUARE_L); }
    "]" { return token(Symbols.SQUARE_R, TokenType.SQUARE_R); }
    "{" { return token(Symbols.CURLY_L, TokenType.CURLY_L); }
    "}" { return token(Symbols.CURLY_R, TokenType.CURLY_R); }

    // SEPARATORS
    "::" { return token(Symbols.DOUBLE_COLON, TokenType.DOUBLE_COLON); }
    ":"  { return token(Symbols.COLON, TokenType.COLON); }
    ";"  { return token(Symbols.SEMICOLON, TokenType.SEMICOLON); }
    "."  { return token(Symbols.DOT, TokenType.DOT); }
    ","  { return token(Symbols.COMMA, TokenType.COMMA); }

    // LOGICAL OPERATORS
    ">"  { return token(Symbols.GREATER, TokenType.GREATER); }
    "<"  { return token(Symbols.SMALLER, TokenType.SMALLER); }
    "==" { return token(Symbols.EQUALS, TokenType.EQUALS); }
    "!=" { return token(Symbols.NOT_EQUALS, TokenType.NOT_EQUALS); }
    "&&" { return token(Symbols.AND, TokenType.AND); }
    "!"  { return token(Symbols.NOT, TokenType.NOT); }

    // MATH OPERATORS
    "=" { return token(Symbols.ATTRIBUTION, TokenType.ATTRIBUTION); }
    "+" { return token(Symbols.ADDITION, TokenType.ADDITION); }
    "-" { return token(Symbols.SUBTRACTION, TokenType.SUBTRACTION); }
    "*" { return token(Symbols.MULTIPLICATION, TokenType.MULTIPLICATION); }
    "/" { return token(Symbols.DIVISION, TokenType.DIVISION); }
    "%" { return token(Symbols.MOD, TokenType.MOD); }

    // Identifier
    {Identifier}  { return token(Symbols.IDENTIFIER, TokenType.IDENTIFIER, yytext()); }

}

<MULTI_COMMENT>{
   "-}"     { yybegin(YYINITIAL); }
   [^"-}"]  {}
}

// ERROR
[^] { throw new UnexpectedException( String.format("%d:%d %s is not a valid character.", yyline + 1, yycolumn + 1, yytext())); }

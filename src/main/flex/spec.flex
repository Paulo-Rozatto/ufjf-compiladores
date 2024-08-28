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
  return token(Symbols.EOF);
%eofval}


%{
    private int tokensSize;

    public int getTokensSize() {
        return  tokensSize;
    };

    private Symbol token(int type) {
        tokensSize += 1;
        return  new Symbol(type, yyline+1, yycolumn+1);
    }

    private Symbol token(int type, Object value) {
        tokensSize += 1;
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

LiteralChar = \'([^\']|\\n|\\t|\\b|\\r|\\\\|\\')\'

Comment         = "--" {InputCharacter}* {LineTerminator}? // provavelmente o ? serve caso o comentario esteja no final do arquivo

%state MULTI_COMMENT

%%

<YYINITIAL>{
    // Nothing to do
    {WhiteSpace} {}
    {Comment}    {}
    "{-"         { yybegin(MULTI_COMMENT); }

    // Keywords
    "if"       { return token(Symbols.IF); }
    "then"     { return token(Symbols.THEN); }
    "else"     { return token(Symbols.ELSE); }
    "print"    { return token(Symbols.PRINT); }
    "read"     { return token(Symbols.READ); }
    "iterate"  { return token(Symbols.ITERATE); }
    "return"   { return token(Symbols.RETURN); }
    "new"      { return token(Symbols.NEW); }
    "data"     { return token(Symbols.DATA); }

    // Primitives
    "Int"           { return token(Symbols.INT); }
    "Float"         { return token(Symbols.FLOAT); }
    "Char"          { return token(Symbols.CHAR); }
    "Bool"          { return token(Symbols.BOOL); }
    {PrimitiveLike} { return token(Symbols.CUSTOM, yytext()); }

    // Literals
    {LiteralInt}     { return token(Symbols.LIT_INT, Integer.parseInt(yytext())); }
    {LiteralFloat}   { return token(Symbols.LIT_FLOAT, Float.parseFloat(yytext())); }
    {LiteralChar}    { return token(Symbols.LIT_CHAR, yytext()); }
    "true"           { return token(Symbols.LIT_BOOL, true); }
    "false"          { return token(Symbols.LIT_BOOL, false); }
    "null"           { return token(Symbols.LIT_NULL); }

    // BRACES
    "(" { return token(Symbols.ROUND_L); }
    ")" { return token(Symbols.ROUND_R); }
    "[" { return token(Symbols.SQUARE_L); }
    "]" { return token(Symbols.SQUARE_R); }
    "{" { return token(Symbols.CURLY_L); }
    "}" { return token(Symbols.CURLY_R); }

    // SEPARATORS
    "::" { return token(Symbols.DOUBLE_COLON); }
    ":"  { return token(Symbols.COLON); }
    ";"  { return token(Symbols.SEMICOLON); }
    "."  { return token(Symbols.DOT); }
    ","  { return token(Symbols.COMMA); }

    // LOGICAL OPERATORS
    ">"  { return token(Symbols.GREATER); }
    "<"  { return token(Symbols.SMALLER); }
    "==" { return token(Symbols.EQUALS); }
    "!=" { return token(Symbols.NOT_EQUALS); }
    "&&" { return token(Symbols.AND); }
    "!"  { return token(Symbols.NOT); }

    // MATH OPERATORS
    "=" { return token(Symbols.ATTRIBUTION); }
    "+" { return token(Symbols.ADDITION); }
    "-" { return token(Symbols.SUBTRACTION); }
    "*" { return token(Symbols.MULTIPLICATION); }
    "/" { return token(Symbols.DIVISION); }
    "%" { return token(Symbols.MOD); }

    // Identifier
    {Identifier}  { return token(Symbols.IDENTIFIER, yytext()); }

}

<MULTI_COMMENT>{
   "-}"     { yybegin(YYINITIAL); }
   [^"-}"]  {}
}

// ERROR
[^] { throw new UnexpectedException( String.format("%d:%d %s is not a valid character.", yyline + 1, yycolumn + 1, yytext())); }

/*
   André Luiz Cunha
   Paulo Victor de M. Rozatto
 */


package de.jflex;

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
        return new Token(type, yyline+1, yycolumn+1, null);
    }

    private Token token(TokenType type, Object value) {
        return new Token(type, yyline+1, yycolumn+1, value);
    }

%}

%init{
    tokensSize = 0;
%init}

LineTerminator	= \r|\n|\r\n
InputCharacter	= [^\r\n]
WhiteSpace      = {LineTerminator}|[ \t\f]
Identifier      = [:jletter:][:jletterdigit:]*
Int             = [0-9]+
Float           = [0-9]*\.[0-9]+
Char            = \'[:jletter:]\'
Comment         = "--" {InputCharacter}* {LineTerminator}? // provavelmente o ? serve caso o comentario esteja no final do arquivo


%%

<YYINITIAL>{
    // Nothing to do
    {WhiteSpace} {}
    {Comment}    {}

    // Identifier
    {Identifier}  { return token(TokenType.IDENTIFIER, yytext()); }

    // Keywords
    "if"       { return token(TokenType.KEYWORD, yytext().toUpperCase()); }
    "then"     { return token(TokenType.KEYWORD, yytext().toUpperCase()); }
    "else"     { return token(TokenType.KEYWORD, yytext().toUpperCase()); }
    "print"    { return token(TokenType.KEYWORD, yytext().toUpperCase()); }
    "read"     { return token(TokenType.KEYWORD, yytext().toUpperCase()); }
    "iterate"  { return token(TokenType.KEYWORD, yytext().toUpperCase()); }
    "return"   { return token(TokenType.KEYWORD, yytext().toUpperCase()); }

    // Primitives


    // Literals
    {Int}     { return token(TokenType.LIT_INT, Integer.parseInt(yytext())); }
    {Float}   { return token(TokenType.LIT_INT, Integer.parseInt(yytext())); }
    {Char}    { return token(TokenType.LIT_CHAR, yytext()); }
    "true"    { return token(TokenType.LIT_BOOL, true); }
    "false"   { return token(TokenType.LIT_BOOL, false); }
    "null"    { return token(TokenType.LIT_NULL); }


    // Error
//    [^]		{ throw new UnexpectedCharacterException(yytext(), yyline, yycolumn); }
    [^] {}
}
/*
   André Luiz Cunha
   Paulo Victor de M. Rozatto
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
            return new Token(type, yyline+1, yycolumn+1, Optional.empty());
        }

    private Token token(TokenType type, Object value) {
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
LiteralChar     = \'[:jletter:]\'
Comment         = "--" {InputCharacter}* {LineTerminator}? // provavelmente o ? serve caso o comentario esteja no final do arquivo


%%

<YYINITIAL>{
    // Nothing to do
    {WhiteSpace} {}
    {Comment}    {}

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
    {PrimitiveLike} { throw new UnexpectedException( String.format("%d:%d %s is not a valid primitive", yyline + 1, yycolumn + 1, yytext()));    }

    // Literals
    {LiteralInt}     { return token(TokenType.LIT_INT, Integer.parseInt(yytext())); }
    {LiteralFloat}   { return token(TokenType.LIT_FLOAT, Float.parseFloat(yytext())); }
    {LiteralChar}    { return token(TokenType.LIT_CHAR, yytext()); }
    "true"           { return token(TokenType.LIT_BOOL, true); }
    "false"          { return token(TokenType.LIT_BOOL, false); }
    "null"           { return token(TokenType.LIT_NULL); }

    // Identifier
    {Identifier}  { return token(TokenType.IDENTIFIER, yytext()); }


    // Error todo: throw error
    [^] {}
}
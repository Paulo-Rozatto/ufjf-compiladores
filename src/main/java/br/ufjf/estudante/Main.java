/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */

package br.ufjf.estudante;

import br.ufjf.estudante.tokens.TokenType;
import de.jflex.Lexer;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;
import lang.Parser;
import lang.Symbols;

import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Nenhum arquivo passado como parametro!");
            return;
        }

        try {
            Lexer lexer = new Lexer(new FileReader(args[0]));
            Symbol t = lexer.next_token();

            while (t.sym != 0) {
                String tk = TokenType.valueOf(Symbols.terminalNames[t.sym]).label;
                if (t.value != null) {
                    tk += ":" + t.value;
                }
                System.out.println(tk);
                t = lexer.next_token();
            }

            System.out.printf("%d tokens lidos.\n", lexer.getTokensSize());
            Scanner scanner = new Lexer(new FileReader(args[0]));
            Parser p = new Parser(scanner);
            p.parse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */

package br.ufjf.estudante;

import br.ufjf.estudante.tokens.Token;
import de.jflex.Lexer;
import java_cup.runtime.Symbol;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Nenhum arquivo passado como parametro!");
            return;
        }

        try {
            Lexer lexer = new Lexer(new FileReader(args[0]));
            Symbol t = lexer.next_token();

            while (t != null) {
                System.out.println(((Token) t.value).toText());
                t = lexer.next_token();
            }
            System.out.printf("%d tokens lidos.\n", lexer.getTokensSize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
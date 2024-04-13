package br.ufjf.estudante;

import br.ufjf.estudante.tokens.Token;
import de.jflex.Lexer;

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
            Token t = lexer.nextToken();

            while (t != null) {
                System.out.println(t.toText());
                t = lexer.nextToken();
            }
            System.out.printf("%d tokens lidos.\n", lexer.getTokensSize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
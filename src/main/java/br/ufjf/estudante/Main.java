package br.ufjf.estudante;

import br.ufjf.estudante.tokens.Token;
import de.jflex.Lexer;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Lexer lexer = new Lexer(new FileReader(args[0]));
            Token t = lexer.nextToken();

            while (t != null) {
                System.out.println(t.toText());
                t = lexer.nextToken();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
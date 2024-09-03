package br.ufjf.estudante;

import de.jflex.Lexer;
import java_cup.runtime.Scanner;
import lang.Parser;
import lang.ast.SuperNode;
import lang.parser.ParseAdaptor;

import java.io.FileReader;

public class ParserAdapted implements ParseAdaptor {
    @Override
    public SuperNode parseFile(String path) {
        try {
            Scanner scanner = new Lexer(new FileReader(path));
            Parser p = new Parser(scanner);
            return (SuperNode) (p.parse().value);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

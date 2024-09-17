package br.ufjf.estudante.util;

public class VisitException extends RuntimeException {
  public VisitException(String message, int line) {
    super("Erro na linha " + line + ": " + message);
  }
}

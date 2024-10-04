/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/

package br.ufjf.estudante.util;

public class VisitException extends RuntimeException {
  public VisitException(String message, int line) {
    super("Erro na linha " + line + ": " + message);
  }

  public VisitException() {
    super("");
  }
}

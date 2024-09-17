package br.ufjf.estudante.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Messenger {
  private static final Logger logger = Logger.getAnonymousLogger();

  private Messenger() {}

  public static void error(String message, int line) {
    logger.log(Level.SEVERE, "Erro na linha {0} -> {1}", new Object[] {line, message});
  }
}

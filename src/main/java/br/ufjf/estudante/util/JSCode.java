/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/

package br.ufjf.estudante.util;

public class JSCode {
    public final static String _div = """
    function _div(a, b) {
      return Number.isInteger(a) ? Math.floor(a / b) : a / b;
    }

    """;

    public final static String _read = """
            import { createInterface } from "node:readline/promises";
            import { stdin as input, stdout as output } from "node:process";

            async function _read() {
              const readLine = createInterface({ input, output });
              let answer = (await readLine.question("")).trim();
              readLine.close();

              if (/^\\d+$/.test(answer)) {
                return parseInt(answer);
              }
              if (/^\\d*\\.\\d+$/.test(answer)) {
                return parseFloat(answer);
              }
              if (/^'.'$/.test(answer)) {
                return answer[1];
              }
              if (answer === "true") {
                return true;
              }
              if (answer === "false") {
                return false;
              }

              throw new Error(`Invalid input: ${answer}`);
            }
            
            """;

    private JSCode() {}

}

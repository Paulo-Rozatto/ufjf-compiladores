/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
import br.ufjf.estudante.util.VisitException;
import br.ufjf.estudante.visitor.VisitorJavaScript;
import de.jflex.Lexer;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java_cup.runtime.Scanner;
import lang.Parser;
import lang.ast.Program;
import org.testng.annotations.Test;

public class JavaScriptTest {
  private static final String rightPath = "src/test/resources/data/semantic/right/";

  private void test(String folder, String file) throws VisitException, FileNotFoundException {
    file = folder + file + ".lan";
    Scanner lexer = new Lexer(new FileReader(file));
    Parser p = new Parser(lexer);
    Program prog;
    try {
      prog = (Program) (p.parse().value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    VisitorJavaScript visitor = new VisitorJavaScript();
    prog.accept(visitor);
    System.out.println(visitor.getCode());
  }

  @Test(groups = {"accept"})
  public void accept_teste0() throws Exception {
    test(rightPath, "teste0");
  }

  @Test(groups = {"accept"})
  public void accept_teste10() throws Exception {
    test(rightPath, "teste10");
  }

  @Test(groups = {"accept"})
  public void accept_teste11() throws Exception {
    test(rightPath, "teste11");
  }

  @Test(groups = {"accept"})
  public void accept_teste12() throws Exception {
    test(rightPath, "teste12");
  }

  @Test(groups = {"accept"})
  public void accept_teste1eMeio() throws Exception {
    test(rightPath, "teste1eMeio");
  }

  @Test(groups = {"accept"})
  public void accept_teste1() throws Exception {
    test(rightPath, "teste1");
  }

  //  @Test(groups = {"accept"})
  //  public void accept_teste2() throws Exception {
  //    test(rightPath, "teste2");
  //  }

  @Test(groups = {"accept"})
  public void accept_teste3() throws Exception {
    test(rightPath, "teste3");
  }

  @Test(groups = {"accept"})
  public void accept_teste4() throws Exception {
    test(rightPath, "teste4");
  }

  @Test(groups = {"accept"})
  public void accept_teste5() throws Exception {
    test(rightPath, "teste5");
  }

  @Test(groups = {"accept"})
  public void accept_teste6() throws Exception {
    test(rightPath, "teste6");
  }

  @Test(groups = {"accept"})
  public void accept_teste7() throws Exception {
    test(rightPath, "teste7");
  }

  @Test(groups = {"accept"})
  public void accept_teste8() throws Exception {
    test(rightPath, "teste8");
  }

  @Test(groups = {"accept"})
  public void accept_teste9() throws Exception {
    test(rightPath, "teste9");
  }
}

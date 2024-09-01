import br.ufjf.estudante.ast.Program;
import br.ufjf.estudante.visitor.VisitorInterpreter;
import de.jflex.Lexer;
import java_cup.runtime.Scanner;
import lang.Parser;
import org.testng.annotations.Test;

import java.io.FileReader;

public class SemanticTest {
    final private static String rightPath = "src/test/resources/data/semantic/right/";
    final private static String wrongPath = "src/test/resources/data/semantic/wrong/";

    private void test(String folder, String file) throws Exception {
        file = folder + file + ".lan";
        Scanner lexer = new Lexer(new FileReader(file));
        Parser p = new Parser(lexer);

        Program prog = (Program) (p.parse().value);
        VisitorInterpreter interpreter = new VisitorInterpreter();
        prog.accept(interpreter);
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

    @Test(groups = {"accept"})
    public void accept_teste2() throws Exception {
        test(rightPath, "teste2");
    }

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


    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrAND() throws Exception {
        test(wrongPath, "attrAND");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrFALSE() throws Exception {
        test(wrongPath, "attrFALSE");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_data0() throws Exception {
        test(wrongPath, "data0");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_data1() throws Exception {
        test(wrongPath, "data1");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_data2() throws Exception {
        test(wrongPath, "data2");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_data3() throws Exception {
        test(wrongPath, "data3");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_function0() throws Exception {
        test(wrongPath, "function0");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_function1() throws Exception {
        test(wrongPath, "function1");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_function_call_expr() throws Exception {
        test(wrongPath, "function_call_expr");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_function_call_ret_use() throws Exception {
        test(wrongPath, "function_call_ret_use");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_ifelse_oneCMD() throws Exception {
        test(wrongPath, "ifelse_oneCMD");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_if_oneCMD() throws Exception {
        test(wrongPath, "if_oneCMD");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_if_teste() throws Exception {
        test(wrongPath, "if_teste");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_instanciate1() throws Exception {
        test(wrongPath, "instanciate1");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_instanciate() throws Exception {
        test(wrongPath, "instanciate");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_main_args() throws Exception {
        test(wrongPath, "main_args");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_main_missing() throws Exception {
        test(wrongPath, "main_missing");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_parameters1() throws Exception {
        test(wrongPath, "parameters1");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_parameters2() throws Exception {
        test(wrongPath, "parameters2");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_parameters() throws Exception {
        test(wrongPath, "parameters");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_return() throws Exception {
        test(wrongPath, "return");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_teste8() throws Exception {
        test(wrongPath, "teste8");
    }

}
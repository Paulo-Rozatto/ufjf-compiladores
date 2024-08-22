import de.jflex.Lexer;
import java_cup.runtime.Scanner;
import lang.Parser;
import org.testng.annotations.Test;

import java.io.FileReader;


public class SyntaxTest {
    final private static String rightPath = "src/test/resources/data/syntax/right/";
    final private static String wrongPath = "src/test/resources/data/syntax/wrong/";

    private void accept(String file) throws Exception {
        Scanner scanner = null;
        Parser p = null;

        file = rightPath + file + ".lan";
        scanner = new Lexer(new FileReader(file));
        p = new Parser(scanner);
        p.parse();
    }


    private void reject(String file) throws Exception {
        Scanner scanner = null;
        Parser p = null;

        file = wrongPath + file + ".lan";
        scanner = new Lexer(new FileReader(file));
        p = new Parser(scanner);

        p.parse();
    }

    @Test(groups = {"accept"})
    public void accept_attrADD() throws Exception {
        accept("attrADD");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrADD() throws Exception {
        reject("attrADD");
    }

    @Test(groups = {"accept"})
    public void accept_attrAND() throws Exception {
        accept("attrAND");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrAND() throws Exception {
        reject("attrAND");
    }

    @Test(groups = {"accept"})
    public void accept_attrCHARESCAPE1() throws Exception {
        accept("attrCHARESCAPE1");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrCHARESCAPE1() throws Exception {
        reject("attrCHARESCAPE1");
    }

    @Test(groups = {"accept"})
    public void accept_attrCHARESCAPE2() throws Exception {
        accept("attrCHARESCAPE2");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrCHARESCAPE2() throws Exception {
        reject("attrCHARESCAPE2");
    }

    @Test(groups = {"accept"})
    public void accept_attrCHARESCAPE3() throws Exception {
        accept("attrCHARESCAPE3");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrCHARESCAPE3() throws Exception {
        reject("attrCHARESCAPE3");
    }

    @Test(groups = {"accept"})
    public void accept_attrCHAR() throws Exception {
        accept("attrCHAR");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrCHAR() throws Exception {
        reject("attrCHAR");
    }

    @Test(groups = {"accept"})
    public void accept_attrCMD() throws Exception {
        accept("attrCMD");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrCMD() throws Exception {
        reject("attrCMD");
    }

    @Test(groups = {"accept"})
    public void accept_attrDIV() throws Exception {
        accept("attrDIV");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrDIV() throws Exception {
        reject("attrDIV");
    }

    @Test(groups = {"accept"})
    public void accept_attrEQ() throws Exception {
        accept("attrEQ");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrEQ() throws Exception {
        reject("attrEQ");
    }

    @Test(groups = {"accept"})
    public void accept_attrFALSE() throws Exception {
        accept("attrFALSE");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrFALSE() throws Exception {
        reject("attrFALSE");
    }

    @Test(groups = {"accept"})
    public void accept_attrFloat() throws Exception {
        accept("attrFloat");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrFloat() throws Exception {
        reject("attrFloat");
    }

    @Test(groups = {"accept"})
    public void accept_attrLT() throws Exception {
        accept("attrLT");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrLT() throws Exception {
        reject("attrLT");
    }

    @Test(groups = {"accept"})
    public void accept_attrMOD() throws Exception {
        accept("attrMOD");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrMOD() throws Exception {
        reject("attrMOD");
    }

    @Test(groups = {"accept"})
    public void accept_attrMULT() throws Exception {
        accept("attrMULT");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrMULT() throws Exception {
        reject("attrMULT");
    }

    @Test(groups = {"accept"})
    public void accept_attrNEQ() throws Exception {
        accept("attrNEQ");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrNEQ() throws Exception {
        reject("attrNEQ");
    }

    @Test(groups = {"accept"})
    public void accept_attrNULL() throws Exception {
        accept("attrNULL");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrNULL() throws Exception {
        reject("attrNULL");
    }

    @Test(groups = {"accept"})
    public void accept_attrSUB() throws Exception {
        accept("attrSUB");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrSUB() throws Exception {
        reject("attrSUB");
    }

    @Test(groups = {"accept"})
    public void accept_attrTRUE() throws Exception {
        accept("attrTRUE");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_attrTRUE() throws Exception {
        reject("attrTRUE");
    }

    @Test(groups = {"accept"})
    public void accept_chainIf() throws Exception {
        accept("chainIf");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_chainIf() throws Exception {
        reject("chainIf");
    }

    @Test(groups = {"accept"})
    public void accept_data() throws Exception {
        accept("data");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_data() throws Exception {
        reject("data");
    }

    @Test(groups = {"accept"})
    public void accept_function_call_expr() throws Exception {
        accept("function_call_expr");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_function_call_expr() throws Exception {
        reject("function_call_expr");
    }

    @Test(groups = {"accept"})
    public void accept_function_call() throws Exception {
        accept("function_call");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_function_call() throws Exception {
        reject("function_call");
    }

    @Test(groups = {"accept"})
    public void accept_function_call_ret() throws Exception {
        accept("function_call_ret");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_function_call_ret() throws Exception {
        reject("function_call_ret");
    }

    @Test(groups = {"accept"})
    public void accept_function_call_ret_use2() throws Exception {
        accept("function_call_ret_use2");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_function_call_ret_use2() throws Exception {
        reject("function_call_ret_use2");
    }

    @Test(groups = {"accept"})
    public void accept_function_call_ret_use() throws Exception {
        accept("function_call_ret_use");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_function_call_ret_use() throws Exception {
        reject("function_call_ret_use");
    }

    @Test(groups = {"accept"})
    public void accept_function() throws Exception {
        accept("function");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_function() throws Exception {
        reject("function");
    }

    @Test(groups = {"accept"})
    public void accept_ifelse_oneCMD() throws Exception {
        accept("ifelse_oneCMD");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_ifelse_oneCMD() throws Exception {
        reject("ifelse_oneCMD");
    }

    @Test(groups = {"accept"})
    public void accept_if_oneCMD() throws Exception {
        accept("if_oneCMD");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_if_oneCMD() throws Exception {
        reject("if_oneCMD");
    }

    @Test(groups = {"accept"})
    public void accept_instanciate() throws Exception {
        accept("instanciate");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_instanciate() throws Exception {
        reject("instanciate");
    }

    @Test(groups = {"accept"})
    public void accept_iterateCMD() throws Exception {
        accept("iterateCMD");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_iterateCMD() throws Exception {
        reject("iterateCMD");
    }

    @Test(groups = {"accept"})
    public void accept_iterate_oneCMD() throws Exception {
        accept("iterate_oneCMD");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_iterate_oneCMD() throws Exception {
        reject("iterate_oneCMD");
    }

    @Test(groups = {"accept"})
    public void accept_parameter() throws Exception {
        accept("parameter");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_parameter() throws Exception {
        reject("parameter");
    }

    @Test(groups = {"accept"})
    public void accept_printCMD() throws Exception {
        accept("printCMD");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_printCMD() throws Exception {
        reject("printCMD");
    }

    @Test(groups = {"accept"})
    public void accept_readCMD() throws Exception {
        accept("readCMD");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_readCMD() throws Exception {
        reject("readCMD");
    }

    @Test(groups = {"accept"})
    public void accept_returnCMD() throws Exception {
        accept("returnCMD");
    }

    @Test(groups = {"reject"}, expectedExceptions = {Exception.class})
    public void reject_returnCMD() throws Exception {
        reject("returnCMD");
    }

}

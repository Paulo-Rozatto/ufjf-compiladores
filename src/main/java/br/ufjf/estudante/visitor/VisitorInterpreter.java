/*
   André Luiz Cunha de Oliveira  - 201935020
   Paulo Victor de M. Rozatto  - 201935027
 */
package br.ufjf.estudante.visitor;

import br.ufjf.estudante.util.Pair;
import de.jflex.Lexer;
import java_cup.runtime.Symbol;
import lang.Symbols;
import lang.ast.Command;
import lang.ast.CommandAttribution;
import lang.ast.CommandCall;
import lang.ast.CommandIf;
import lang.ast.CommandIterate;
import lang.ast.CommandPrint;
import lang.ast.CommandRead;
import lang.ast.CommandReturn;
import lang.ast.CommandsList;
import lang.ast.Data;
import lang.ast.Declarations;
import lang.ast.Definition;
import lang.ast.DefinitionsList;
import lang.ast.Expression;
import lang.ast.ExpressionArithmetic;
import lang.ast.ExpressionBoolean;
import lang.ast.ExpressionCall;
import lang.ast.ExpressionNew;
import lang.ast.ExpressionsList;
import lang.ast.Function;
import lang.ast.LValue;
import lang.ast.Literal;
import lang.ast.LiteralBool;
import lang.ast.LiteralChar;
import lang.ast.LiteralFloat;
import lang.ast.LiteralInt;
import lang.ast.LiteralNull;
import lang.ast.Node;
import lang.ast.Params;
import lang.ast.Program;
import lang.ast.ReturnTypes;
import lang.ast.Type;
import lang.ast.TypeCustom;
import lang.ast.TypePrimitive;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class VisitorInterpreter implements Visitor {
    private final Stack<Map<String, Pair<Type, Literal>>> environments = new Stack<>();
    private Map<String, Definition> definitionMap;
    private boolean isReturn = false;

    @Override
    public void visit(CommandAttribution attribution) {
        Expression expression = attribution.getExpression();
        expression.accept(this);
        Literal literal = expression.evaluate();
        attribution.getlValue().accept(this);
        attribution.getlValue().set(literal);
    }

    @Override
    public void visit(CommandCall call) {
        Definition def = definitionMap.get(call.getId());

        if (def == null) {
            throw new RuntimeException("Função não declarada! " + call.getId());
        }

        if (def.getClass() != Function.class) {
            throw new RuntimeException(call.getId() + " não é uma função!");
        }

        Function function = (Function) def;
        Map<String, Pair<Type, Literal>> env = new HashMap<>();

        if (function.getParams() != null) {
            if (call.getParams() == null || function.getParams().size() != call.getParams().getExpressions().size()) {
                int expectedNumberSizes = function.getParams().size();
                int gotNumberSizes = call.getParams() == null ? 0 : call.getParams().getExpressions().size();

                throw new RuntimeException(String.format(
                        "Esperava-se %d variaveis de retorno, mas %d foram obtidas.",
                        expectedNumberSizes, gotNumberSizes
                ));
            }

            List<String> paramIds = function.getParams().keySet().stream().toList();
            List<Type> paramTypes = function.getParams().values().stream().toList();
            List<Expression> paramExps = call.getParams().getExpressions();

            for (int i = 0; i < function.getParams().size(); i++) {
                String id = paramIds.get(i);
                Type type = paramTypes.get(i);
                Expression exp = paramExps.get(i);

                exp.accept(this);

                env.put(id, new Pair<>(type, exp.evaluate()));
            }
        }

        environments.add(env);

        function.accept(this);
        isReturn = false;

        env = environments.pop();

        List<String> returnVars = call.getReturnVars();
        if (returnVars != null && !returnVars.isEmpty()) {
            ReturnTypes returnTypes = function.getReturnTypes();

            if (returnTypes == null || returnTypes.getTypes().size() != returnVars.size()) {
                int expectedNumberSizes = returnTypes == null ? 0 : returnTypes.getTypes().size();
                int gotNumberSizes = returnVars.size();

                throw new RuntimeException(String.format(
                        "Esperava-se %d variaveis de retorno, mas %d foram obtidas.",
                        expectedNumberSizes, gotNumberSizes
                ));
            }

            for (int i = 0; i < returnVars.size(); i++) {
                String varId = returnVars.get(i);
                Pair<Type, Literal> value = env.get(i + "return");
                env.remove(i + "return");

                if (value == null) {
                    throw new RuntimeException(
                            String.format("Faltando retorno %d, variavel %s", i + 1, varId)
                    );
                }


                Pair<Type, Literal> pv = environments.peek().get(varId);

                if (pv != null && pv.getFirst().getC() != value.getFirst().getC()) {
                    throw new RuntimeException(String.format("Não se pode atribuir tipo %s em variável %s", pv.getFirst().getC(), value.getFirst().getC()));
                }

                environments.peek().put(varId, value);
            }
        }
    }

    @Override
    public void visit(CommandIf node) {
        Expression expression = node.getExpression();
        expression.accept(this);
        LiteralBool exp = (LiteralBool) expression.evaluate();
        if (exp.getValue()) {
            node.getThen().accept(this);
        } else if (node.getOtherwise() != null) {
            node.getOtherwise().accept(this);
        }
    }

    @Override
    public void visit(CommandIterate iterate) {
        Expression expression = iterate.getExpression();
        expression.accept(this);
        Literal exp = expression.evaluate();

        if (exp.getClass() != LiteralInt.class) {
            throw new RuntimeException("Iterate espera um inteiro.");
        }

        int n = ((LiteralInt) exp).getValue();

        // nao especifica que negativo tem que ser erro
        if (n <= 0) {
            return;
        }

        for (int i = 0; i < n; i++) {
            iterate.getCommand().accept(this);
        }

    }

    @Override
    public void visit(Command node) {

    }

    @Override
    public void visit(CommandPrint print) {
        Expression expression = print.getExpression();
        expression.accept(this);
        Object value = expression.evaluate();

        System.out.print(value);
    }

    @Override
    public void visit(CommandRead read) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        java_cup.runtime.Scanner lexer = new Lexer(new StringReader(input));
        Literal literal;
        try {
            Symbol t = lexer.next_token();
            literal = switch (t.sym) {
                case Symbols.LIT_INT -> new LiteralInt((int) t.value, read.getLine());
                case Symbols.LIT_FLOAT -> new LiteralFloat((float) t.value, read.getLine());
                case Symbols.LIT_CHAR -> new LiteralChar((String) t.value, read.getLine());
                case Symbols.LIT_BOOL -> new LiteralBool((boolean) t.value, read.getLine());
                default -> throw new RuntimeException("Entrada inválida, espera-se um Int, Float, Char ou Bool.");
            };
            read.getLValue().accept(this);
            read.getLValue().set(literal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void visit(CommandReturn node) {
        List<Expression> returns = node.getReturns().getExpressions();
        for (int i = 0; i < returns.size(); i++) {
            Expression expression = returns.get(i);
            expression.accept(this);
            Literal value = expression.evaluate();
            environments.peek().put(i + "return", new Pair<>(value.getType(), value));
        }
        isReturn = true;
    }

    @Override
    public void visit(CommandsList commandsList) {
        for (Command command : commandsList.getCommands()) {
            if (isReturn) {
                break;
            }
            command.accept(this);
        }
    }

    @Override
    public void visit(Data node) {

    }

    @Override
    public void visit(Declarations node) {

    }

    @Override
    public void visit(Definition node) {
    }

    @Override
    public void visit(DefinitionsList definitions) {
        definitionMap = definitions.getDefinitionMap();

        Definition main = definitionMap.get("main");

        if (main == null) {
            throw new RuntimeException("Programa não tem main!");
        } else if (main.getClass() != Function.class) {
            throw new RuntimeException("Error: main não é uma função!");
        } else {
            CommandCall mainCall = new CommandCall("main", null, null, main.getLine());
            mainCall.accept(this);
        }
    }

    @Override
    public void visit(ExpressionArithmetic expression) {
    }

    @Override
    public void visit(ExpressionBoolean node) {

    }

    @Override
    public void visit(ExpressionCall call) {
        Definition def = definitionMap.get(call.getId());

        if (def == null) {
            throw new RuntimeException("Função não declarada! " + call.getId());
        }

        if (def.getClass() != Function.class) {
            throw new RuntimeException(call.getId() + " não é uma função!");
        }

        int numberReturns = ((Function) def).getReturnTypes().getTypes().size();
        List<String> returnVars = new ArrayList<>();
        for (int i = 0; i < numberReturns; i++) {
            returnVars.add(String.valueOf(i));
        }

        CommandCall call1 = new CommandCall(call.getId(), call.getParams(), returnVars, call.getLine());
        call1.accept(this);
    }

    @Override
    public void visit(Expression node) {

    }

    @Override
    public void visit(ExpressionNew expNew) {
//        expNew.accept(this);
        expNew.evaluate();
    }

    @Override
    public void visit(ExpressionsList node) {

    }

    @Override
    public void visit(Function function) {
//        System.out.println("Function: " + function.getId());
        function.getCommandsList().accept(this);
    }

    @Override
    public void visit(LiteralBool node) {

    }

    @Override
    public void visit(LiteralChar node) {

    }

    @Override
    public void visit(LiteralFloat node) {

    }

    @Override
    public void visit(LiteralInt node) {

    }

    @Override
    public void visit(LiteralNull node) {

    }

    @Override
    public void visit(LValue node) {

    }

    @Override
    public void visit(Node node) {

    }

    @Override
    public void visit(Params node) {

    }

    @Override
    public void visit(Program node) {
        node.getDefList().accept(this);
    }

    @Override
    public void visit(ReturnTypes node) {

    }

    @Override
    public void visit(TypeCustom node) {

    }

    @Override
    public void visit(Type node) {

    }

    @Override
    public void visit(TypePrimitive node) {

    }

    public Map<String, Pair<Type, Literal>> getEnv() {
        return environments.peek();
    }

    public Map<String, Definition> getDefinitions() {
        return definitionMap;
    }
}

package br.ufjf.estudante.visitor;

import br.ufjf.estudante.ast.Command;
import br.ufjf.estudante.ast.CommandAttribution;
import br.ufjf.estudante.ast.CommandCall;
import br.ufjf.estudante.ast.CommandIf;
import br.ufjf.estudante.ast.CommandIterate;
import br.ufjf.estudante.ast.CommandPrint;
import br.ufjf.estudante.ast.CommandRead;
import br.ufjf.estudante.ast.CommandReturn;
import br.ufjf.estudante.ast.CommandsList;
import br.ufjf.estudante.ast.Data;
import br.ufjf.estudante.ast.Declarations;
import br.ufjf.estudante.ast.Definition;
import br.ufjf.estudante.ast.DefinitionsList;
import br.ufjf.estudante.ast.Expression;
import br.ufjf.estudante.ast.ExpressionArithmetic;
import br.ufjf.estudante.ast.ExpressionBoolean;
import br.ufjf.estudante.ast.ExpressionCall;
import br.ufjf.estudante.ast.ExpressionNew;
import br.ufjf.estudante.ast.ExpressionsList;
import br.ufjf.estudante.ast.Function;
import br.ufjf.estudante.ast.LValue;
import br.ufjf.estudante.ast.LiteralBool;
import br.ufjf.estudante.ast.LiteralChar;
import br.ufjf.estudante.ast.LiteralFloat;
import br.ufjf.estudante.ast.LiteralInt;
import br.ufjf.estudante.ast.LiteralNull;
import br.ufjf.estudante.ast.Node;
import br.ufjf.estudante.ast.Params;
import br.ufjf.estudante.ast.Program;
import br.ufjf.estudante.ast.ReturnTypes;
import br.ufjf.estudante.ast.Type;
import br.ufjf.estudante.ast.TypeCustom;
import br.ufjf.estudante.ast.TypePrimitive;
import br.ufjf.estudante.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class VisitorInterpreter implements Visitor {
    private final Stack<Map<String, Pair<Type, Object>>> enviroments = new Stack<>();
    private Map<String, Definition> definitionMap;

    @Override
    public void visit(CommandAttribution node) {

    }

    @Override
    public void visit(CommandCall call) {
        System.out.println("CommandCall");
        Definition def = definitionMap.get(call.getId());

        if (def == null) {
            throw new RuntimeException("Função não declarada! " + call.getId());
        }

        if (def.getClass() != Function.class) {
            System.out.println(call.getId() + " não é uma função!");
            throw new RuntimeException(call.getId() + " não é uma função!");
        }

        Function function = (Function) def;
        Map<String, Pair<Type, Object>> env = new HashMap<>();

        if (function.getParams() != null) {
            if (call.getParams() == null || function.getParams().size() != call.getParams().getExpressions().size()) {
                throw new RuntimeException(call.getId() + " possui erro na quantidade de parâmetros");
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

        enviroments.add(env);

        // todo: consertar compilacao
        // todo: pegar variaveis de retorno

        call.accept(this);

        enviroments.pop();
    }

    @Override
    public void visit(CommandIf node) {

    }

    @Override
    public void visit(CommandIterate node) {

    }

    @Override
    public void visit(Command node) {

    }

    @Override
    public void visit(CommandPrint node) {

    }

    @Override
    public void visit(CommandRead node) {

    }

    @Override
    public void visit(CommandReturn node) {

    }

    @Override
    public void visit(CommandsList node) {

    }

    @Override
    public void visit(Data node) {

    }

    @Override
    public void visit(Declarations node) {

    }

    @Override
    public void visit(Definition node) {
        System.out.println("Definition");

//        if (node.getClass() == Data.class) {
//            return;
//        }
//
//        node.accept(this);
    }

    @Override
    public void visit(DefinitionsList node) {
        System.out.println("DefinitionsList!");
        definitionMap = node.getDefinitionMap();

        Definition main = definitionMap.get("main");

        if (main == null) {
            System.out.println("Warning: Programa não têm main!");
        } else if (main.getClass() != Function.class) {
            System.out.println("Error: main não é uma função!");
        } else {
            CommandCall mainCall = new CommandCall("main", null, null, main.getLine());
            mainCall.accept(this);
        }
    }

    @Override
    public void visit(ExpressionArithmetic node) {

    }

    @Override
    public void visit(ExpressionBoolean node) {

    }

    @Override
    public void visit(ExpressionCall node) {
        System.out.println("ExpressionCall");
    }

    @Override
    public void visit(Expression node) {

    }

    @Override
    public void visit(ExpressionNew node) {

    }

    @Override
    public void visit(ExpressionsList node) {

    }

    @Override
    public void visit(Function node) {

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
        System.out.println("Program");
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
}

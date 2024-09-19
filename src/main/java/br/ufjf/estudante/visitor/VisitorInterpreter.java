/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package br.ufjf.estudante.visitor;

import br.ufjf.estudante.util.Messenger;
import br.ufjf.estudante.util.Pair;
import br.ufjf.estudante.util.VisitException;
import com.google.common.collect.Multimap;
import de.jflex.Lexer;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
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
import lang.ast.LiteralArray;
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

public class VisitorInterpreter implements Visitor {
  private final Stack<Map<String, Pair<Type, Literal>>> environments = new Stack<>();
  private Multimap<String, Definition> definitionMap;
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
    runFunction(call.getId(), call.getParams(), call.getReturnVars(), call.getLine());
  }

  @Override
  public void visit(CommandIf node) {
    Expression expression = node.getExpression();
    expression.accept(this);

    try {
      LiteralBool exp = (LiteralBool) expression.evaluate();
      if (exp.getValue()) {
        node.getThen().accept(this);
      } else if (node.getOtherwise() != null) {
        node.getOtherwise().accept(this);
      }
    } catch (ClassCastException e) {
      throw new VisitException("Expressão não booleana!", node.getLine());
    }
  }

  @Override
  public void visit(CommandIterate iterate) {
    Expression expression = iterate.getExpression();
    expression.accept(this);
    Literal exp = expression.evaluate();

    if (exp.getClass() != LiteralInt.class) {
      throw new VisitException("Iterate espera um inteiro.", iterate.getLine());
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
  public void visit(Command node) {}

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
      literal =
          switch (t.sym) {
            case Symbols.LIT_INT -> new LiteralInt((int) t.value, read.getLine());
            case Symbols.LIT_FLOAT -> new LiteralFloat((float) t.value, read.getLine());
            case Symbols.LIT_CHAR -> new LiteralChar((String) t.value, read.getLine());
            case Symbols.LIT_BOOL -> new LiteralBool((boolean) t.value, read.getLine());
            default ->
                throw new VisitException(
                    "Entrada inválida, espera-se um Int, Float, Char ou Bool.", read.getLine());
          };
      read.getLValue().accept(this);
      read.getLValue().set(literal);
    } catch (Exception e) {
      Messenger.error(e.getMessage(), read.getLine());
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
  public void visit(Data node) {}

  @Override
  public void visit(Declarations node) {}

  @Override
  public void visit(Definition node) {}

  @Override
  public void visit(DefinitionsList definitions) {
    definitionMap = definitions.getDefinitionMap();

    List<String> datas =
        definitionMap.keys().stream().filter(e -> Character.isUpperCase(e.charAt(0))).toList();
    Set<String> uniqueDatas = new HashSet<>();

    datas.forEach(
        data -> {
          if (uniqueDatas.contains(data)) {
            throw new VisitException(
                "Tipo '" + data + "' duplicado",
                definitionMap.get(data).iterator().next().getLine());
          }
          uniqueDatas.add(data);
        });

    Collection<Definition> mainCollection = definitionMap.get("main");

    if (mainCollection.isEmpty()) {
      throw new VisitException("Programa não tem main!", definitions.getLine());
    }

    if (mainCollection.size() > 1) {
      throw new VisitException("Programa tem mais de uma main!", definitions.getLine());
    }

    Definition main = mainCollection.iterator().next();

    CommandCall mainCall = new CommandCall("main", null, null, main.getLine());
    mainCall.accept(this);
  }

  @Override
  public void visit(ExpressionArithmetic expression) {}

  @Override
  public void visit(ExpressionBoolean node) {}

  @Override
  public void visit(ExpressionCall call) {
    runFunction(call.getId(), call.getParams(), null, call.getLine());
  }

  @Override
  public void visit(Expression node) {}

  @Override
  public void visit(ExpressionNew expNew) {
    expNew.evaluate();
  }

  @Override
  public void visit(ExpressionsList node) {}

  @Override
  public void visit(Function function) {
    function.getCommandsList().accept(this);
  }

  @Override
  public void visit(LiteralBool node) {}

  @Override
  public void visit(LiteralChar node) {}

  @Override
  public void visit(LiteralFloat node) {}

  @Override
  public void visit(LiteralInt node) {}

  @Override
  public void visit(LiteralNull node) {}

  @Override
  public void visit(LValue node) {}

  @Override
  public void visit(Node node) {}

  @Override
  public void visit(Params node) {}

  @Override
  public void visit(Program node) {
    node.getDefList().accept(this);
  }

  @Override
  public void visit(ReturnTypes node) {}

  @Override
  public void visit(TypeCustom node) {}

  @Override
  public void visit(Type node) {}

  @Override
  public void visit(TypePrimitive node) {}

  public Map<String, Pair<Type, Literal>> getEnv() {
    return environments.peek();
  }

  public Multimap<String, Definition> getDefinitions() {
    return definitionMap;
  }

  private boolean matchTypes(Map<String, Type> params, List<Literal> args) {
    // se os dois forem null, retorna true
    if (params == null && args == null) {
      return true;
    }

    // se apenas um for null, entao false
    if (params == null || args == null) {
      return false;
    }

    List<Type> types = params.values().stream().toList();
    if (types.size() != args.size()) {
      return false;
    }

    Type expectedType;
    Literal literal;
    for (int i = 0; i < types.size(); i++) {
      expectedType = types.get(i);
      literal = args.get(i);

      if (expectedType.getDimensions() > 1) {
        if (!(literal instanceof LiteralArray)) {
          return false;
        }

        if (((LiteralArray) literal).getElType().getLiteralClass()
            != expectedType.getLiteralClass()) {
          return false;
        }
      } else if (expectedType.getLiteralClass() != literal.getClass()) {
        return false;
      }
    }

    return true;
  }

  private void runFunction(String id, ExpressionsList params, List<String> returnVars, int line) {
    Collection<Definition> defCollection = definitionMap.get(id);

    if (defCollection.isEmpty()) {
      throw new VisitException("Função não declarada! " + id, line);
    }

    // Faça a avaliação dos argumentos passados
    List<Literal> args =
        params == null
            ? null
            : params.getExpressions().stream()
                .map(
                    e -> {
                      e.accept(this);
                      return e.evaluate();
                    })
                .toList();

    // Encontrar uma função que tenha o mesmo número de parâmetros e tipos de parâmetros
    Function function = null;
    boolean isFunction = false;
    boolean matchArgs = false;

    for (Definition def : defCollection.stream().toList()) {
      isFunction = def.getClass() == Function.class;

      if (!isFunction) {
        continue;
      }

      function = (Function) def;

      // auxiliar checa tamanho e tipo
      matchArgs = matchTypes(function.getParams(), args);

      if (matchArgs) {
        break;
      }
    }

    if (!isFunction) {
      throw new VisitException("Nenhuma função declarada com id '" + id + "'", line);
    }

    if (!matchArgs) {
      throw new VisitException(
          String.format(
              "Não há definição de %s que receba %s.",
              id, args == null ? "[]" : args.stream().map(Literal::getClass)),
          line);
    }

    // Cria enviroment com variaveis passadas por parametro
    Map<String, Pair<Type, Literal>> env = new HashMap<>();

    if (function.getParams() != null) {
      List<String> paramIds = function.getParams().keySet().stream().toList();
      for (int i = 0; i < function.getParams().size(); i++) {
        Literal arg = args.get(i);
        env.put(paramIds.get(i), new Pair<>(arg.getType(), arg));
      }
    }

    // Executa função
    environments.add(env);
    function.accept(this);
    isReturn = false;
    env = environments.pop();

    if (returnVars == null) {
      if (function.getReturnTypes() == null) {
        return;
      }

      // Retornos sem variavel atribuida -> f()[0], f()[1], etc
      List<Type> returnTypes = function.getReturnTypes().getTypes();
      for (int i = 0; i < returnTypes.size(); i++) {
        Pair<Type, Literal> value = env.get(i + "return");
        env.remove(i + "return");

        if (value == null) {
          throw new VisitException(
              String.format("Faltando %dº retorno.", i + 1), function.getLine());
        }

        if (value.getFirst().getClass() != returnTypes.get(i).getClass()) {
          throw new VisitException(
              String.format(
                  "Esperava-se retorno tipo %s, obtido tipo %s",
                  returnTypes.get(i).getClass().getCanonicalName(),
                  value.getFirst().getClass().getCanonicalName()),
              function.getLine());
        }

        environments.peek().put(String.valueOf(i), value);
      }

      return;
    }

    if (function.getReturnTypes() == null) {
      throw new VisitException(String.format("Função '%s' não têm retorno", id), line);
    }

    // Retornos com variavel atribuida -> f()<x>, f()<x,y>, etc
    for (int i = 0; i < returnVars.size(); i++) {
      String varId = returnVars.get(i);
      Pair<Type, Literal> value = env.get(i + "return");
      // env.remove(i + "return"); // acho que nao precisa

      if (value == null) {
        throw new VisitException(
            String.format("Faltando %dº retorno, variavel %s", i + 1, varId), line);
      }

      Pair<Type, Literal> pv = environments.peek().get(varId);

      if (pv != null && pv.getFirst().getLiteralClass() != value.getFirst().getLiteralClass()) {
        throw new VisitException(
            String.format(
                "Não se pode atribuir tipo %s em variável %s",
                value.getFirst().getLiteralClass().getCanonicalName(),
                pv.getFirst().getLiteralClass().getCanonicalName()),
            line);
      }

      environments.peek().put(varId, value);
    }
  }
}

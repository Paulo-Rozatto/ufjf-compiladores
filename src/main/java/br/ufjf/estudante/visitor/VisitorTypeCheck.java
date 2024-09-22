package br.ufjf.estudante.visitor;

import br.ufjf.estudante.types.SArray;
import br.ufjf.estudante.types.SBoolean;
import br.ufjf.estudante.types.SChar;
import br.ufjf.estudante.types.SCustom;
import br.ufjf.estudante.types.SError;
import br.ufjf.estudante.types.SFloat;
import br.ufjf.estudante.types.SFunction;
import br.ufjf.estudante.types.SInt;
import br.ufjf.estudante.types.SNull;
import br.ufjf.estudante.types.SOr;
import br.ufjf.estudante.types.SType;
import br.ufjf.estudante.util.Messenger;
import br.ufjf.estudante.util.VisitException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
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

public class VisitorTypeCheck implements Visitor {
  private final Map<String, SCustom> customMap = new HashMap<>();
  private final Multimap<String, SFunction> functionMap = ArrayListMultimap.create();
  private final Stack<SType> stack = new Stack<>();
  private Map<String, SType> environment;

  private boolean hasError = false;
  private boolean isReturn = false;

  private final SType[] primitives = {
    SInt.newSInt(), SFloat.newSFloat(), SChar.newSChar(), SBoolean.newSBoolean()
  };
  private final SType orPrimitives = new SOr(primitives);

  @Override
  public void visit(CommandAttribution attribution) {
    attribution.getExpression().accept(this);
    attribution.getlValue().accept(this);

    SType variable = stack.pop(), value = stack.pop();

    if (attribution.getExpression() instanceof LValue && SNull.newSNull().match(value)) {
      error(
          attribution.getLine(),
          "Variável '%s' indefinida.",
          ((LValue) attribution.getExpression()).getId());
      value = SError.newSError();
    }

    if (variable instanceof SError) {
      environment.put(attribution.getlValue().getId(), SError.newSError());
      return;
    }

    if (SNull.newSNull().match(variable)) {
      environment.put(attribution.getlValue().getId(), value);
      return;
    }

    if (!variable.match(value)) {
      error(
          attribution.getLine(),
          "Não se pode atribuir %s em variável do tipo %s.",
          value,
          variable);
      environment.put(attribution.getlValue().getId(), SError.newSError());
    }
  }

  @Override
  public void visit(CommandCall call) {
    SType match = matchFunction(call.getId(), call.getParams(), call.getLine());
    List<String> vars = call.getReturnVars();

    if (match instanceof SError) {
      if (vars != null) {
        for (String varId : vars) {
          environment.put(varId, SError.newSError());
        }
      }
      return;
    }

    SFunction matchFunction = (SFunction) match;

    if (vars != null && !vars.isEmpty()) {
      for (int i = 0; i < vars.size(); i++) {
        String id = vars.get(i);
        SType varType = environment.get(id);
        SType retType = matchFunction.getReturn(i);

        if (retType == null) {
          error(call.getLine(), "Função não tem retorno para o índice %d.", i);
          retType = SError.newSError();
        }

        if (varType != null && !varType.match(retType)) {
          error(call.getLine(), "Não se pode atribuir %s em variável tipo %s.", retType, varType);
          retType = SError.newSError();
        }

        environment.put(id, retType);
      }
    }
  }

  @Override
  public void visit(CommandIf commandIf) {
    commandIf.getExpression().accept(this);
    SType expression = stack.pop();

    if (!expression.match(SBoolean.newSBoolean())) {
      error(commandIf.getLine(), "Condicional espera Bool, recebeu %s.", expression);
    }

    isReturn = false;
    commandIf.getThen().accept(this);
    boolean thenReturn = isReturn;
    isReturn = false;

    if (commandIf.getOtherwise() != null) {
      commandIf.getOtherwise().accept(this);
    }

    isReturn = isReturn && thenReturn;
  }

  @Override
  public void visit(CommandIterate iterate) {
    iterate.getExpression().accept(this);

    SType type = stack.pop();
    if (!type.match(SInt.newSInt())) {
      error(iterate.getLine(), "Iterate espera Int, recebeu %s.", type);
    }

    iterate.getCommand().accept(this);
  }

  @Override
  public void visit(Command node) {}

  @Override
  public void visit(CommandPrint print) {
    print.getExpression().accept(this);
    SType type = stack.pop();

    if (!orPrimitives.match(type)) {
      error(print.getLine(), "Print não aceita tipo: ", type);
    }
  }

  @Override
  public void visit(CommandRead node) {
    node.getLValue().accept(this);
    SType type = stack.pop();

    if (!type.match(SNull.newSNull()) && !orPrimitives.match(type)) {
      error(node.getLine(), "Não se pode ler tipo %s.", type);
      stack.push(SError.newSError());
      return;
    }

    stack.push(orPrimitives);
  }

  @Override
  public void visit(CommandReturn commandReturn) {
    List<Expression> returns = commandReturn.getReturns().getExpressions();
    for (int i = 0; i < returns.size(); i++) {
      returns.get(i).accept(this);
      SType value = stack.pop();
      String position = String.valueOf(i);
      SType envReturn = environment.get(position);

      if (envReturn == null) {
        environment.put(position, value);
      } else if (!envReturn.match(value)) {
        error(
            commandReturn.getLine(),
            "Função não pode retornar %s e %s na mesma posição.",
            value,
            envReturn);
        environment.put(position, SError.newSError());
      }
    }
    isReturn = true;
  }

  @Override
  public void visit(CommandsList commandsList) {
    for (Command command : commandsList.getCommands()) {
      command.accept(this);
    }
  }

  @Override
  public void visit(Data data) {
    if (customMap.get(data.getId()) != null) {
      // Se o tipo está duplicado, encerra a verificação aqui, pois não tem como inferir qual
      // deinfição está certa para usar nas próximas inferências
      throw new VisitException("Tipo " + data.getId() + "já declarado!", data.getLine());
    }

    Map<String, SType> fields = new HashMap<>();
    data.getDeclarations()
        .forEach(
            (id, type) -> {
              fields.put(id, type.getSType());
            });

    TypeCustom typeCustom = TypeCustom.getType(data.getId());
    SCustom s = (SCustom) typeCustom.getSType();
    s.setFields(fields);
    customMap.put(data.getId(), s);
  }

  @Override
  public void visit(Declarations node) {}

  @Override
  public void visit(Definition node) {}

  @Override
  public void visit(DefinitionsList definitions) {
    List<Function> functionList = new ArrayList<>();

    // Definicoes podem ser Datas ou Functions
    // Datas podem ser visitadas e registradas na visita
    // Funções precisam ser registradas e depois visitadas
    for (Map.Entry<String, Definition> entry : definitions.getDefinitionMap().entries()) {
      Definition definition = entry.getValue();

      // Visita Data se for o caso e pula para a iteração seguinte
      if (definition instanceof Data) {
        definition.accept(this);
        continue;
      }

      // Senão, pegue a função e os seus tipos de argumentos
      Function function = (Function) definition;
      SType[] argTypes =
          function.getParams() == null
              ? null
              : function.getParams().values().stream().map(Type::getSType).toArray(SType[]::new);
      SType[] retTypes =
          function.getReturnTypes() == null
              ? null
              : function.getReturnTypes().getTypes().stream()
                  .map(Type::getSType)
                  .toArray(SType[]::new);

      // Crie o tipo Singleton do visitor de type check
      SFunction newFunction = new SFunction(argTypes, retTypes);

      // Cheque se a função com o mesmo nome e parâmetros já não foi definida
      Collection<SFunction> definedFunctions = functionMap.get(function.getId());
      if (!definedFunctions.isEmpty()) {
        definedFunctions.forEach(
            sFunction -> {
              if (sFunction.match(newFunction)) {
                // Se a função está duplicada, encerra a verificação de tipos, porque pode gerar
                // ambiguidade na verificação de tipos assim como Datas duplicados
                throw new VisitException("Função duplicada!", function.getLine());
              }
            });
      }

      // Então registre a função
      functionMap.put(definition.getId(), newFunction);
      functionList.add(function);
    }

    Collection<SFunction> mains = functionMap.get("main");

    if (mains.isEmpty()) {
      // Não tem sentido continuar a verificação de um programa sem main
      throw new VisitException("Programa não têm main!", definitions.getLine());
    }

    // Registrados as declarações, visite as funções
    for (Function function : functionList) {
      function.accept(this);
    }
  }

  @Override
  public void visit(ExpressionArithmetic expression) {
    if (Objects.equals(expression.getOp(), "-") && expression.getLeft() == null) {
      expression.getRight().accept(this);
      SType right = stack.pop();

      if (notNumber(right)) {
        error(expression.getLine(), "Esperava-se tipo numérico, obteve-se %s.", right);
        stack.push(SError.newSError());
        return;
      }

      stack.push(SInt.newSInt());
      return;
    }

    expression.getLeft().accept(this);
    expression.getRight().accept(this);

    SType right = stack.pop(), left = stack.pop();

    if (expression.getOp().equals("%")
        && !(left.match(SInt.newSInt()) && right.match(SInt.newSInt()))) {
      error(expression.getLine(), "Esperava-se dois inteiros, obteve-se %s %% %s", left, right);
      stack.push(SError.newSError());
      return;
    }

    if (notNumber(left) || notNumber(right)) {
      error(
          expression.getLine(),
          "Esperava-se tipos numéricos para realizar %s %s %s",
          left,
          expression.getOp(),
          right);
      stack.push(SError.newSError());
      return;
    }

    if (!left.match(right)) {
      error(
          expression.getLine(),
          "Esperava-se tipos iguais para realizar entre %s %s %s",
          left,
          expression.getOp(),
          right);
      stack.push(SError.newSError());
      return;
    }

    if (Objects.equals(expression.getOp(), "<")) {
      stack.push(SBoolean.newSBoolean());
    } else {
      stack.push(left);
    }
  }

  @Override
  public void visit(ExpressionBoolean expression) {
    String op = expression.getOp();
    if (op.equals("!")) {
      expression.getRight().accept(this);
      SType right = stack.pop();

      if (!right.match(SBoolean.newSBoolean())) {
        error(expression.getLine(), "Não se pode negar tipo %s.", right);
        stack.push(SError.newSError());
        return;
      }

      stack.push(right);
      return;
    }

    expression.getLeft().accept(this);
    expression.getRight().accept(this);

    SType right = stack.pop(), left = stack.pop();

    if (op.equals("&&")
        || op.equals("||")
            && (!left.match(SBoolean.newSBoolean()) || !right.match(SBoolean.newSBoolean()))) {
      error(expression.getLine(), "Esperava-se booleanos para realizar %s %s %s.", left, op, right);
      stack.push(SError.newSError());
      return;
    }

    if (!left.match(right)) {
      error(expression.getLine(), "Não se pode realizar comparação entre %s e %s.", left, right);
      stack.push(SError.newSError());
      return;
    }

    stack.push(SBoolean.newSBoolean());
  }

  @Override
  public void visit(ExpressionCall call) {
    SType match = matchFunction(call.getId(), call.getParams(), call.getLine());

    if (match instanceof SError) {
      stack.push(match);
      return;
    }

    SFunction matchFunction = (SFunction) match;
    call.getModifier().accept(this);
    SType modifier = stack.pop();

    if (!modifier.match(SInt.newSInt())) {
      error(call.getLine(), "Tipo %s não pode ser usado para acessar retorno.", modifier);
    }

    if (modifier instanceof SError) {
      stack.push(SError.newSError());
      return;
    }

    SType[] returns = matchFunction.getReturnTypes();
    Integer index = ((SInt) modifier).getValue();

    if (index == null) {
      stack.push(new SOr(returns));
      return;
    }

    if (index > returns.length - 1) {
      error(
          call.getLine(),
          "Tentando acessar retorno de indice %d em função que retorna apenas %d valores.",
          index,
          returns.length);
      stack.push(SError.newSError());
      return;
    }

    stack.push(returns[index]);
  }

  @Override
  public void visit(Expression node) {}

  @Override
  public void visit(ExpressionNew newExpression) {
    SType type = newExpression.getType().getSType();

    if (type instanceof SCustom) {
      type = customMap.get(((SCustom) type).getId());
    }

    if (!(type instanceof SCustom) && !(type instanceof SArray)) {
      error(newExpression.getLine(), "Não se pode instanciar tipo %s.", type);
      stack.push(SError.newSError());
      return;
    }

    stack.push(type);
  }

  @Override
  public void visit(ExpressionsList node) {}

  @Override
  public void visit(Function function) {
    if (Objects.equals(function.getId(), "main") && function.getParams() != null) {
      error(function.getLine(), "Main não aceita argumentos!");
    }

    Map<String, SType> env = new HashMap<>();

    if (function.getParams() != null) {
      function.getParams().forEach((id, type) -> env.put(id, type.getSType()));
    }

    environment = env;

    isReturn = false;
    function.getCommandsList().accept(this);

    if (function.getReturnTypes() != null) {
      if (!isReturn) {
        error(function.getLine(), "Faltando retorno!");
        return;
      }

      List<Type> typeList = function.getReturnTypes().getTypes();
      SType returnType;

      for (int i = 0; i < typeList.size(); i++) {
        returnType = env.get(String.valueOf(i));

        if (returnType == null) {
          error(function.getLine(), "Faltando %dº retorno.", (i + 1));
          continue;
        }

        SType expectedType = typeList.get(i).getSType();
        if (!returnType.match(expectedType)) {
          error(
              function.getLine(),
              "Esperava retorno tipo %s, obtido tipo %s",
              expectedType,
              returnType);
        }
      }
    }
  }

  @Override
  public void visit(LiteralBool node) {
    stack.push(SBoolean.newSBoolean());
  }

  @Override
  public void visit(LiteralChar node) {
    stack.push(SChar.newSChar());
  }

  @Override
  public void visit(LiteralFloat node) {
    stack.push(SFloat.newSFloat());
  }

  @Override
  public void visit(LiteralInt literal) {
    stack.push(new SInt(literal.getValue()));
  }

  @Override
  public void visit(LiteralNull node) {
    stack.push(SNull.newSNull());
  }

  @Override
  public void visit(LValue lValue) {
    SType variable = environment.get(lValue.getId());
    if (variable == null) {
      if (!lValue.getModifiers().isEmpty()) {
        error(lValue.getLine(), "Não se pode realizar acessos em variável não definida!");
        stack.push(SError.newSError());
        return;
      }

      stack.push(SNull.newSNull());
      return;
    }

    if (variable instanceof SError) {
      stack.push(variable);
      return;
    }

    boolean error = false;
    // Acesso de campos e arrays no mesmo for loop
    for (Object modifier : lValue.getModifiers()) {

      // Se tiver acessando como string, assume ser tipo custom.
      if (modifier instanceof String) {
        // Se não for tipo custom, então erro.
        if (!(variable instanceof SCustom)) {
          error(lValue.getLine(), "Tipo %s não tem campos.", variable);
          error = true;
          break;
        }

        // Faz o casting da variável para custom e pega sua definição registrada
        variable = customMap.get(((SCustom) variable).getId());

        // Atualiza a variável com o acesso do campo
        variable = ((SCustom) variable).getFieldType((String) modifier);

        // Se a variável estiver vazia é porque o campo é inexistente
        if (variable == null) {
          error(lValue.getLine(), "Campo '%s' inexistente.", modifier);
          error = true;
          break;
        }

        // Se não houve erro, pode passar para a próxima iteração do laço
        continue;
      }

      // Se é expressão, pega o resultado dela como modifier.
      if (modifier instanceof Expression) {
        ((Expression) modifier).accept(this);
        modifier = stack.pop();
      }

      // Se chegou aqui, o acesso é de array, logo o acesso tem que ser inteiro.
      if (modifier instanceof SInt) {
        variable = ((SArray) variable).getType();
        continue;
      }

      // Se chegou aqui, é erro de acesso de array.
      error(lValue.getLine(), "Esperava-se Int para acessar array, obtido %s.", modifier);
      error = true;
      break;
    }

    if (error) {
      stack.push(SError.newSError());
    } else {
      stack.push(variable);
    }
  }

  @Override
  public void visit(Node node) {}

  @Override
  public void visit(Params node) {}

  @Override
  public void visit(Program program) {
    program.getDefList().accept(this);
    if (hasError) {
      throw new VisitException("Erros detectados!", 0);
    }
  }

  @Override
  public void visit(ReturnTypes node) {}

  @Override
  public void visit(TypeCustom node) {}

  @Override
  public void visit(Type node) {}

  private SType matchFunction(String id, ExpressionsList params, int line) {
    Collection<SFunction> functionCollection = functionMap.get(id);

    if (functionCollection.isEmpty()) {
      error(line, "Função não definida.");
      return SError.newSError();
    }

    SType[] callArgs =
        params == null
            ? null
            : params.getExpressions().stream()
                .map(
                    expression -> {
                      expression.accept(this);
                      return stack.pop();
                    })
                .toArray(SType[]::new);

    SFunction callFunction = new SFunction(callArgs);

    SFunction matchFunction = null;
    for (SFunction defFunction : functionCollection) {
      if (defFunction.match(callFunction)) {
        matchFunction = defFunction;
        break;
      }
    }

    if (matchFunction == null) {
      error(line, "Função não definida para esses parâmetros.");
      return SError.newSError();
    }

    return matchFunction;
  }

  private boolean notNumber(SType type) {
    return !type.match(SInt.newSInt()) && !type.match(SFloat.newSFloat());
  }

  private void error(int line, String message, Object... args) {
    Messenger.error(String.format(message, args), line);
    hasError = true;
  }
}

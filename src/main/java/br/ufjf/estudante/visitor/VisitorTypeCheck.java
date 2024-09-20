package br.ufjf.estudante.visitor;

import br.ufjf.estudante.singletons.SCustom;
import br.ufjf.estudante.singletons.SFunction;
import br.ufjf.estudante.singletons.SType;
import br.ufjf.estudante.util.VisitException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
  //  private final Stack<Map<String, SType>> enviroments = new Stack<>();
  private Map<String, SType> enviroment;

  @Override
  public void visit(CommandAttribution node) {}

  @Override
  public void visit(CommandCall node) {}

  @Override
  public void visit(CommandIf node) {}

  @Override
  public void visit(CommandIterate node) {}

  @Override
  public void visit(Command node) {}

  @Override
  public void visit(CommandPrint node) {}

  @Override
  public void visit(CommandRead node) {}

  @Override
  public void visit(CommandReturn node) {}

  @Override
  public void visit(CommandsList node) {}

  @Override
  public void visit(Data data) {
    if (customMap.get(data.getId()) != null) {
      throw new VisitException("Tipo " + data.getId() + "já declarado!", data.getLine());
    }

    // todo: aqui deveria checar se campos são duplicados, mas como estamos usando HashMap campos
    // duplicados são sobreescritos

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

      // Crie o tipo Singleton do visitor de type check
      SFunction newFunction = new SFunction(argTypes);

      // Cheque se a função com o mesmo nome e parâmetros já não foi definida
      Collection<SFunction> definedFunctions = functionMap.get(function.getId());
      if (!definedFunctions.isEmpty()) {
        definedFunctions.forEach(
            sFunction -> {
              if (sFunction.match(newFunction)) {
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
      throw new VisitException("Programa não têm main!", definitions.getLine());
    }

    if (mains.size() > 1) {
      throw new VisitException("Mais de uma main declarada!", definitions.getLine());
    }

    // Registrados as declarações, visite as funções
    for (Function function : functionList) {
      function.accept(this);
    }
  }

  @Override
  public void visit(ExpressionArithmetic node) {}

  @Override
  public void visit(ExpressionBoolean node) {}

  @Override
  public void visit(ExpressionCall node) {}

  @Override
  public void visit(Expression node) {}

  @Override
  public void visit(ExpressionNew node) {}

  @Override
  public void visit(ExpressionsList node) {}

  @Override
  public void visit(Function function) {
    if (Objects.equals(function.getId(), "main") && function.getParams() != null) {
      throw new VisitException("Main não aceita argumentos!", function.getLine());
    }

    Map<String, SType> env = new HashMap<>();

    if (function.getParams() != null) {
      function.getParams().forEach((id, type) -> env.put(id, type.getSType()));
    }

    enviroment = env;

    function.getCommandsList().accept(this);

    if (function.getReturnTypes() != null && function.getReturnTypes().getTypes() != null) {
      List<Type> typeList = function.getReturnTypes().getTypes();
      SType returnType;
      for (int i = 0; i < typeList.size(); i++) {
        returnType = env.get(String.valueOf(i));

        if (returnType == null) {
          throw new VisitException("Faltando " + (i + 1) + "º retorno.", function.getLine());
        }

        SType expectedType = typeList.get(i).getSType();
        if (!returnType.match(expectedType)) {
          throw new VisitException(
              String.format("Esperava retorno tipo %s, obtido tipo %s", expectedType, returnType),
              function.getLine());
        }
      }
    }
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
  public void visit(Program program) {
    program.getDefList().accept(this);
  }

  @Override
  public void visit(ReturnTypes node) {}

  @Override
  public void visit(TypeCustom node) {}

  @Override
  public void visit(Type node) {}
}

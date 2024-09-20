package br.ufjf.estudante.visitor;

import br.ufjf.estudante.singletons.SCustom;
import br.ufjf.estudante.singletons.SFunction;
import br.ufjf.estudante.singletons.SType;
import br.ufjf.estudante.util.VisitException;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
    for (Map.Entry<String, Definition> definition : definitions.getDefinitionMap().entries()) {
      definition.getValue().accept(this);
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
    SType[] argTypes =
        function.getParams() == null
            ? null
            : function.getParams().values().stream().map(Type::getSType).toArray(SType[]::new);

    SType[] returnTypes =
        function.getReturnTypes() == null
            ? null
            : function.getReturnTypes().getTypes().stream()
                .map(Type::getSType)
                .toArray(SType[]::new);

    SFunction sFunction = new SFunction(argTypes, returnTypes);
    Collection<SFunction> declaredFunctions = functionMap.get(function.getId());

    if (declaredFunctions.isEmpty()) {
      functionMap.put(function.getId(), sFunction);
      return;
    }

    declaredFunctions
        .iterator()
        .forEachRemaining(
            declaredFunction -> {
              if (declaredFunction.match(sFunction)) {
                throw new VisitException("Função já declarada", function.getLine());
              }
            });
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
}

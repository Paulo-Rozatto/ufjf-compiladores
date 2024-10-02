package br.ufjf.estudante.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import lang.ast.TypeArray;
import lang.ast.TypeBool;
import lang.ast.TypeChar;
import lang.ast.TypeCustom;
import lang.ast.TypeFloat;
import lang.ast.TypeInt;

public class VisitorJasmim implements Visitor {
  private final Stack<String> stack = new Stack<>();
  StringBuilder code = new StringBuilder();

  private int indentLevel;

  private static String jasminType(Type type) {
    StringBuilder builder = new StringBuilder();
    String jsmType;

    if (type instanceof TypeArray) {
      throw new RuntimeException("Cara, nao eh para ter TypeArray aqui");
    } else if (type instanceof TypeInt) {
      jsmType = "I";
    } else if (type instanceof TypeFloat) {
      jsmType = "F";
    } else if (type instanceof TypeChar) {
      jsmType = "C";
    } else if (type instanceof TypeBool) {
      jsmType = "Z";
    } else if (type instanceof TypeCustom) {
      jsmType = "L" + ((TypeCustom) type).getId() + ";";
    } else {
      jsmType = "V";
    }

    if (type.getDimensions() > 1) {
      builder.append("[".repeat(type.getDimensions() - 1));
      builder.append(jsmType).append(" ").append(type.getDimensions() - 1);
    } else {
      builder.append(jsmType);
    }

    return builder.toString();
  }

  @Override
  public void visit(Program program) {
    indentLevel = 0;

    program.getDefList().accept(this);

    code.append(stack.pop());
  }

  @Override
  public void visit(DefinitionsList definitions) {
    StringBuilder builder = new StringBuilder();

    definitions
        .getDefinitionMap()
        .values()
        .forEach(
            definition -> {
              definition.accept(this);
              builder.append(stack.pop()).append("\n\n");
            });

    stack.push(builder.toString());
  }

  @Override
  public void visit(Data data) {
    StringBuilder builder = new StringBuilder(".class public ");
    builder.append(data.getId()).append("\n").append(".super java/lang/Object").append("\n\n");
    List<Map.Entry<String, Type>> arrays = new ArrayList<>();

    for (Map.Entry<String, Type> entry : data.getDeclarations().entrySet()) {
      String id = entry.getKey();
      Type type = entry.getValue();
      builder.append(".field public ").append(id).append(jasminType(type)).append("\n");

      if (type.getDimensions() > 1) {
        arrays.add(entry);
      }
    }

    builder.append("\n").append(".method public <init>()V").append("\n");
    // todo: inicializar classe e instaciar os arrays aqui

    stack.push(builder.toString());
  }

  @Override
  public void visit(Function node) {}

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
  public void visit(Declarations node) {}

  @Override
  public void visit(Definition node) {}

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
  public void visit(ReturnTypes node) {}

  @Override
  public void visit(TypeCustom node) {}

  @Override
  public void visit(Type node) {}
}

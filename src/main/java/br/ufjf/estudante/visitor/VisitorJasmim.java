package br.ufjf.estudante.visitor;

import br.ufjf.estudante.util.JasmimCode;
import br.ufjf.estudante.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
  private final StringBuilder code = new StringBuilder();
  private Stack<String> typeStack;
  private List<Pair<String, String>> vars; // id, type
  private int indentLevel;
  private int limitStack = 0, limitLocals = 0;

  public String getCode() {
    return code.toString();
  }

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
    //    indentLevel = 0;
    code.append(".class public Main\n").append(".super java/lang/Object\n\n");

    code.append(JasmimCode.default_init).append("\n");

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
    //    List<Map.Entry<String, Type>> arrays = new ArrayList<>();

    for (Map.Entry<String, Type> entry : data.getDeclarations().entrySet()) {
      String id = entry.getKey();
      Type type = entry.getValue();
      builder.append(".field public ").append(id).append(jasminType(type)).append("\n");

      //      if (type.getDimensions() > 1) {
      //        arrays.add(entry);
      //      }
    }
    builder.append("\n").append(JasmimCode.default_init);

    // Arrays precisam ser inicializados, mas nao eh aqui
    //    builder.append("\n").append(".method public <init>()V").append("\n");
    //    for(Map.Entry<String, Type> entry : arrays) {
    //      String id = entry.getKey();
    //      Type type = entry.getValue();
    ////      int size =
    //
    //    }

    stack.push(builder.toString());
  }

  @Override
  public void visit(Function function) {
    StringBuilder builder = new StringBuilder(".method public static ");
    builder.append(function.getId()).append("(");

    limitStack = 0;
    limitLocals = 0;

    vars = new ArrayList<>();
    typeStack = new Stack<>();

    if (function.getParams() != null) {
      for (Map.Entry<String, Type> entry : function.getParams().entrySet()) {
        builder.append(jasminType(entry.getValue()));
        vars.add(new Pair<>(entry.getKey(), jasminType(entry.getValue())));
      }

      limitStack = limitLocals = function.getParams().size();
    }

    builder.append(")V\n");

    indentLevel = 0;
    function.getCommandsList().accept(this);

    builder.append("  .limit stack ").append(limitStack).append("\n");
    builder.append("  .limit locals ").append(limitLocals).append("\n");
    builder.append(stack.pop());

    builder.append("\n").append("  return").append("\n");
    builder.append(".end method");

    stack.push(builder.toString());
  }

  @Override
  public void visit(CommandsList commandsList) {
    StringBuilder builder = new StringBuilder("\n");
    //    indentLevel += 1;
    indentLevel = 0;

    for (Command command : commandsList.getCommands()) {
      command.accept(this);
      builder.append("  ".repeat(indentLevel));
      builder.append(stack.pop()).append("\n");
    }

    //    indentLevel -= 1;
    stack.push(builder.toString());
  }

  @Override
  public void visit(CommandAttribution attribution) {
    StringBuilder builder = new StringBuilder();
    String varId = attribution.getlValue().getId();

    attribution.getExpression().accept(this);
    attribution.getlValue().accept(this);

    String variable = stack.pop(),
        value = stack.pop(),
        valueType = typeStack.pop().toLowerCase(Locale.ROOT);
    builder.append(value).append("\n");
    builder.append(valueType).append("store_").append(variable);

    stack.push(builder.toString());
  }

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
  public void visit(LiteralBool node) {
    stack.push("zconst_" + node.getValue());
    typeStack.push("Z");
    limitStack += 1;
  }

  @Override
  public void visit(LiteralChar node) {
    stack.push("cconst_" + node.getValue());
    typeStack.push("C");
    limitStack += 1;
  }

  @Override
  public void visit(LiteralFloat node) {
    stack.push("ldc " + node.getValue());
    typeStack.push("F");
    limitStack += 1;
  }

  @Override
  public void visit(LiteralInt node) {
    stack.push("iconst_" + node.getValue());
    typeStack.push("I");
    limitStack += 1;
  }

  @Override
  public void visit(LiteralNull node) {
    stack.push("aconst_null");
    typeStack.push("A");
    limitStack += 1;
  }

  @Override
  public void visit(LValue lValue) {
    String id = lValue.getId();
    int index = -1;

    for (int i = 0; i < vars.size(); i++) {
      Pair<String, String> entry = vars.get(i);
      if (entry.getFirst().equals(id)) {
        index = i;
        break;
      }
    }

    if (index == -1) {
      limitLocals += 1;
      index = limitLocals - 1;
      vars.add(new Pair<>(id, null));
    }

    //    String type = jasminType(vars.get(index).getSecond()).toLowerCase(Locale.ROOT);
    //    stack.push(type + "store_" + index);
    stack.push(String.valueOf(index));
  }

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

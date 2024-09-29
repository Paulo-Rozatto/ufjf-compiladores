package br.ufjf.estudante.visitor;

import br.ufjf.estudante.util.JSCode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

public class VisitorJavaScript implements Visitor {
  private final Stack<String> stack = new Stack<>();
  private final StringBuilder code = new StringBuilder();
  private Set<String> declaredVars;

  private int indentLevel = 0;
  private boolean hasRead = false;
  private boolean hasDiv = false;
  private boolean isAsync = false;

  @Override
  public void visit(CommandAttribution attribution) {
    StringBuilder builder = new StringBuilder();
    String varId = attribution.getlValue().getId();

    attribution.getExpression().accept(this);
    attribution.getlValue().accept(this);

    String variable = stack.pop(), value = stack.pop();

    if (declaredVars != null && !declaredVars.contains(varId)) {
      declaredVars.add(varId);
      builder.append("var ");
    }

    builder.append(variable).append(" = ").append(value).append(";");

    stack.push(builder.toString());
  }

  @Override
  public void visit(CommandCall call) {
    StringBuilder builder = new StringBuilder();
    call.getParams().accept(this);

    List<String> returns = call.getReturnVars();
    if (returns != null && !returns.isEmpty()) {
      builder.append("var [").append(returns.get(0));

      for (int i = 1; i < returns.size(); i++) {
        builder.append(", ").append(returns.get(i));
      }
      builder.append("] = ");
    }

    builder.append(call.getId());
    builder.append("(");

    if (call.getParams() != null) {
      List<Expression> params = call.getParams().getExpressions();
      params.get(0).accept(this);
      builder.append(stack.pop());

      for (int i = 1; i < params.size(); i++) {
        builder.append(",");
        params.get(i).accept(this);
        builder.append(stack.pop());
      }
    }

    builder.append(");");

    stack.push(builder.toString());
  }

  @Override
  public void visit(CommandIf commandIf) {
    StringBuilder builder = new StringBuilder("if (");
    commandIf.getExpression().accept(this);
    builder.append(stack.pop()).append(") ");
    commandIf.getThen().accept(this);
    builder.append(stack.pop());

    if (commandIf.getOtherwise() != null) {
      builder.append(" else ");
      commandIf.getOtherwise().accept(this);
      builder.append(stack.pop());
    }

    stack.push(builder.toString());
  }

  @Override
  public void visit(CommandIterate iterate) {
    StringBuilder builder = new StringBuilder("for(let $i = 0; $i < ");
    iterate.getExpression().accept(this);
    builder.append(stack.pop()).append("; $i++)");
    iterate.getCommand().accept(this);
    builder.append(stack.pop());

    stack.push(builder.toString());
  }

  @Override
  public void visit(Command node) {}

  @Override
  public void visit(CommandPrint print) {
    StringBuilder builder = new StringBuilder("process.stdout.write(`${");
    print.getExpression().accept(this);
    builder.append(stack.pop()).append("}`);");
    stack.push(builder.toString());
  }

  @Override
  public void visit(CommandRead node) {
    hasRead = isAsync = true;
    StringBuilder builder = new StringBuilder();
    String varId = node.getLValue().getId();

    node.getLValue().accept(this);
    String variable = stack.pop();

    if (declaredVars != null && !declaredVars.contains(varId)) {
      declaredVars.add(varId);
      builder.append("var ");
    }

    builder.append(variable).append(" = ").append("await _read();");

    stack.push(builder.toString());
  }

  @Override
  public void visit(CommandReturn commandReturn) {
    StringBuilder builder = new StringBuilder("return");

    if (commandReturn.getReturns() != null) {
      List<Expression> returns = commandReturn.getReturns().getExpressions();
      builder.append(" [");
      returns.get(0).accept(this);
      builder.append(stack.pop());

      for (int i = 1; i < returns.size(); i++) {
        returns.get(i).accept(this);
        builder.append(", ").append(stack.pop());
      }
      builder.append("];");
    }
    stack.push(builder.toString());
  }

  @Override
  public void visit(CommandsList commandsList) {
    StringBuilder builder = new StringBuilder("{\n");
    indentLevel += 1;

    for (Command command : commandsList.getCommands()) {
      command.accept(this);
      builder.append("  ".repeat(indentLevel));
      builder.append(stack.pop()).append("\n");
    }

    indentLevel -= 1;
    builder.append("  ".repeat(indentLevel));
    builder.append("}");

    stack.push(builder.toString());
  }

  @Override
  public void visit(Data data) {
    StringBuilder builder = new StringBuilder("function ");
    builder.append(data.getId()).append("(");
    List<String> keys = data.getDeclarations().keySet().stream().toList();

    builder.append(keys.get(0));

    for (int i = 1; i < keys.size(); i++) {
      builder.append(", ").append(keys.get(i));
    }

    builder.append(") {\n");

    builder.append("  this.").append(keys.get(0)).append(" = ");
    builder.append((keys.get(0))).append(";");

    for (int i = 1; i < keys.size(); i++) {
      builder.append("\n");
      builder.append("  this.").append(keys.get(i)).append(" = ");
      builder.append((keys.get(i))).append(";");
    }
    builder.append("\n}");

    stack.push(builder.toString());
  }

  @Override
  public void visit(Declarations node) {}

  @Override
  public void visit(Definition node) {}

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
  public void visit(ExpressionArithmetic expression) {
    StringBuilder builder = new StringBuilder();
    Expression left = expression.getLeft();

    if (expression.getOp().equals("/")) {
      hasDiv = true;
      expression.getRight().accept(this);
      left.accept(this);
      builder.append("_div(").append(stack.pop()).append(", ").append(stack.pop()).append(")");
      stack.push(builder.toString());
      return;
    }

    if (left != null) {
      left.accept(this);
      builder.append(stack.pop()).append(" ");
    }

    expression.getRight().accept(this);
    builder.append(expression.getOp()).append(" ").append(stack.pop());
    stack.push(builder.toString());
  }

  @Override
  public void visit(ExpressionBoolean expression) {
    Expression left = expression.getLeft();
    StringBuilder builder = new StringBuilder();

    if (left != null) {
      left.accept(this);
      builder.append(stack.pop()).append(" ");
    }

    expression.getRight().accept(this);
    builder.append(expression.getOp()).append(stack.pop());
    stack.push(builder.toString());
  }

  @Override
  public void visit(ExpressionCall call) {
    StringBuilder builder = new StringBuilder();
    call.getParams().accept(this);

    builder.append(call.getId());
    builder.append("(");

    if (call.getParams() != null) {
      List<Expression> params = call.getParams().getExpressions();
      params.get(0).accept(this);
      builder.append(stack.pop());

      for (int i = 1; i < params.size(); i++) {
        builder.append(",");
        params.get(i).accept(this);
        builder.append(stack.pop());
      }
    }
    call.getModifier().accept(this);

    builder.append(")[").append(stack.pop()).append("]");

    stack.push(builder.toString());
  }

  @Override
  public void visit(Expression node) {}

  @Override
  public void visit(ExpressionNew expressionNew) {
    StringBuilder builder = new StringBuilder();

    if (expressionNew.getExp() == null) {
      String customType = ((TypeCustom) expressionNew.getType()).getId();
      builder.append("new ").append(customType).append("()");
    } else {
      expressionNew.getExp().accept(this);
      String size = stack.pop();

      int dimensions = expressionNew.getType().getDimensions();
      builder.append("new Array(".repeat(dimensions - 1));
      builder.append(size);
      builder.append(")".repeat(dimensions - 1));
    }

    stack.push(builder.toString());
  }

  @Override
  public void visit(ExpressionsList node) {}

  @Override
  public void visit(Function function) {
    StringBuilder builder = new StringBuilder("function ");
    builder.append(function.getId()).append("(");
    declaredVars = new HashSet<>();

    if (function.getParams() != null && !function.getParams().isEmpty()) {
      List<String> params = function.getParams().keySet().stream().toList();
      builder.append(params.get(0));
      declaredVars.add(params.get(0));

      for (int i = 1; i < params.size(); i++) {
        builder.append(", ").append(params.get(i));
        declaredVars.add(params.get(i));
      }
    }
    builder.append(") ");

    isAsync = false;
    function.getCommandsList().accept(this);
    builder.append(stack.pop());

    if (isAsync) {
      builder.insert(0, "async ");
    }

    stack.push(builder.toString());
  }

  @Override
  public void visit(LiteralBool node) {
    stack.push(String.valueOf(node.getValue()));
  }

  @Override
  public void visit(LiteralChar node) {
    String escaped =
        node.getValue()
            .replace("\\", "\\\\")
            .replace("\t", "\\t")
            .replace("\b", "\\b")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\f", "\\f")
            .replace("'", "\\'")
            .replace("\"", "\\\"");
    stack.push("'" + escaped + "'");
  }

  @Override
  public void visit(LiteralFloat node) {
    stack.push(String.valueOf(node.getValue()));
  }

  @Override
  public void visit(LiteralInt node) {
    stack.push(String.valueOf(node.getValue()));
  }

  @Override
  public void visit(LiteralNull node) {
    stack.push("null");
  }

  @Override
  public void visit(LValue lValue) {
    StringBuilder builder = new StringBuilder(lValue.getId());

    if (!lValue.getModifiers().isEmpty()) {
      for (Object modifier : lValue.getModifiers()) {
        if (modifier instanceof String) {
          builder.append(".").append(modifier);
        } else {
          ((Node) modifier).accept(this);
          builder.append("[").append(stack.pop()).append("]");
        }
      }
    }

    stack.push(builder.toString());
  }

  @Override
  public void visit(Node node) {}

  @Override
  public void visit(Params node) {}

  public String getCode() {
    return code.toString();
  }

  @Override
  public void visit(Program program) {
    indentLevel = 0;
    hasDiv = false;

    program.getDefList().accept(this);

    if (hasRead) {
      code.append(JSCode._read);
    }

    if (hasDiv) {
      code.append(JSCode._div);
    }

    code.append(stack.pop());
    code.append("main();").append("\n");
  }

  @Override
  public void visit(ReturnTypes node) {}

  @Override
  public void visit(TypeCustom node) {}

  @Override
  public void visit(Type node) {}
}

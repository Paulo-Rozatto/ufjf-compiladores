package br.ufjf.estudante.visitor;

import java.util.List;
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
  private StringBuilder code = new StringBuilder();
  private int identLevel = 0;

  @Override
  public void visit(CommandAttribution attribution) {
    attribution.getExpression().accept(this);
    attribution.getlValue().accept(this);

    String variable = stack.pop(), value = stack.pop();
    String attr = String.format("var %s = %s;", variable, value);
    stack.push(attr);
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
    // todo: usar node read line provavelmente
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
    identLevel += 1;

    for (Command command : commandsList.getCommands()) {
      command.accept(this);
      builder.append("  ".repeat(identLevel));
      builder.append(stack.pop()).append("\n");
    }

    identLevel -= 1;
    builder.append("  ".repeat(identLevel));
    builder.append("};");

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

    builder.append("this.").append(keys.get(0)).append(" = ");
    builder.append((keys.get(0))).append(";");

    for (int i = 1; i < keys.size(); i++) {
      builder.append("\n");
      builder.append("this.").append(keys.get(i)).append(" = ");
      builder.append((keys.get(i))).append(";");
    }
    builder.append("}");

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
    // todo: talvez tenha que guardar tipo, divisao inteira ou fazer parse int mesmo e dane-se
    Expression left = expression.getLeft();
    StringBuilder builder = new StringBuilder();

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
  public void visit(ExpressionNew node) {
    // todo: todo
    stack.push("'foo'");
  }

  @Override
  public void visit(ExpressionsList node) {}

  @Override
  public void visit(Function function) {
    StringBuilder builder = new StringBuilder("function ");
    builder.append(function.getId()).append("(");

    if (function.getParams() != null && !function.getParams().isEmpty()) {
      List<String> params = function.getParams().keySet().stream().toList();
      builder.append(params.get(0));

      for (int i = 1; i < params.size(); i++) {
        builder.append(", ").append(params.get(i));
      }
    }

    builder.append(") ");
    function.getCommandsList().accept(this);
    builder.append(stack.pop());

    stack.push(builder.toString());
  }

  @Override
  public void visit(LiteralBool node) {
    stack.push(String.valueOf(node.getValue()));
  }

  @Override
  public void visit(LiteralChar node) {
    stack.push("`" + node.getValue() + "`");
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
  public void visit(LValue node) {
    stack.push(node.getId());
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
    identLevel = 0;
    program.getDefList().accept(this);
    code.append(stack.pop());
    code.append("\n").append("main();");
  }

  @Override
  public void visit(ReturnTypes node) {}

  @Override
  public void visit(TypeCustom node) {}

  @Override
  public void visit(Type node) {}
}

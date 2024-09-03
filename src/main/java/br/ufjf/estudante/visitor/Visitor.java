/*
  André Luiz Cunha de Oliveira  - 201935020
  Paulo Victor de M. Rozatto  - 201935027
*/
package br.ufjf.estudante.visitor;

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
import lang.ast.TypePrimitive;

public interface Visitor {
  void visit(CommandAttribution node);

  void visit(CommandCall node);

  void visit(CommandIf node);

  void visit(CommandIterate node);

  void visit(Command node);

  void visit(CommandPrint node);

  void visit(CommandRead node);

  void visit(CommandReturn node);

  void visit(CommandsList node);

  void visit(Data node);

  void visit(Declarations node);

  void visit(Definition node);

  void visit(DefinitionsList node);

  void visit(ExpressionArithmetic node);

  void visit(ExpressionBoolean node);

  void visit(ExpressionCall node);

  void visit(Expression node);

  void visit(ExpressionNew node);

  void visit(ExpressionsList node);

  void visit(Function node);

  void visit(LiteralBool node);

  void visit(LiteralChar node);

  void visit(LiteralFloat node);

  void visit(LiteralInt node);

  void visit(LiteralNull node);

  void visit(LValue node);

  void visit(Node node);

  void visit(Params node);

  void visit(Program node);

  void visit(ReturnTypes node);

  void visit(TypeCustom node);

  void visit(Type node);

  void visit(TypePrimitive node);
}

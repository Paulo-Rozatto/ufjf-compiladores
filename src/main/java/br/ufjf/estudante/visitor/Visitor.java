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
    public void visit(CommandAttribution node);

    public void visit(CommandCall node);

    public void visit(CommandIf node);

    public void visit(CommandIterate node);

    public void visit(Command node);

    public void visit(CommandPrint node);

    public void visit(CommandRead node);

    public void visit(CommandReturn node);

    public void visit(CommandsList node);

    public void visit(Data node);

    public void visit(Declarations node);

    public void visit(Definition node);

    public void visit(DefinitionsList node);

    public void visit(ExpressionArithmetic node);

    public void visit(ExpressionBoolean node);

    public void visit(ExpressionCall node);

    public void visit(Expression node);

    public void visit(ExpressionNew node);

    public void visit(ExpressionsList node);

    public void visit(Function node);

    public void visit(LiteralBool node);

    public void visit(LiteralChar node);

    public void visit(LiteralFloat node);

    public void visit(LiteralInt node);

    public void visit(LiteralNull node);

    public void visit(LValue node);

    public void visit(Node node);

    public void visit(Params node);

    public void visit(Program node);

    public void visit(ReturnTypes node);

    public void visit(TypeCustom node);

    public void visit(Type node);

    public void visit(TypePrimitive node);
}

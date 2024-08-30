package br.ufjf.estudante.visitor;

import br.ufjf.estudante.ast.Command;
import br.ufjf.estudante.ast.CommandAttribution;
import br.ufjf.estudante.ast.CommandCall;
import br.ufjf.estudante.ast.CommandIf;
import br.ufjf.estudante.ast.CommandIterate;
import br.ufjf.estudante.ast.CommandPrint;
import br.ufjf.estudante.ast.CommandRead;
import br.ufjf.estudante.ast.CommandReturn;
import br.ufjf.estudante.ast.CommandsList;
import br.ufjf.estudante.ast.Data;
import br.ufjf.estudante.ast.Declarations;
import br.ufjf.estudante.ast.Definition;
import br.ufjf.estudante.ast.DefinitionsList;
import br.ufjf.estudante.ast.Expression;
import br.ufjf.estudante.ast.ExpressionArithmetic;
import br.ufjf.estudante.ast.ExpressionBoolean;
import br.ufjf.estudante.ast.ExpressionCall;
import br.ufjf.estudante.ast.ExpressionNew;
import br.ufjf.estudante.ast.ExpressionsList;
import br.ufjf.estudante.ast.Function;
import br.ufjf.estudante.ast.LValue;
import br.ufjf.estudante.ast.LiteralBool;
import br.ufjf.estudante.ast.LiteralChar;
import br.ufjf.estudante.ast.LiteralFloat;
import br.ufjf.estudante.ast.LiteralInt;
import br.ufjf.estudante.ast.LiteralNull;
import br.ufjf.estudante.ast.Node;
import br.ufjf.estudante.ast.Params;
import br.ufjf.estudante.ast.Program;
import br.ufjf.estudante.ast.ReturnTypes;
import br.ufjf.estudante.ast.Type;
import br.ufjf.estudante.ast.TypeCustom;
import br.ufjf.estudante.ast.TypePrimitive;

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

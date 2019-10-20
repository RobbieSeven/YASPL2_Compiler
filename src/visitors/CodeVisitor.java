package visitors;

import java.util.ArrayList;

import nodes.*;

public class CodeVisitor implements Visitor {
	
	private ArrayList<String> parNames;
	
	public CodeVisitor() {
		parNames = new ArrayList<String>();
	}

	@Override
	public String visit(ProgramNode program) {
		StringBuilder codeString = new StringBuilder();
		codeString.append("#include <stdio.h>\n");
		ArrayList<DeclNode> decls = program.getDecls();
		ArrayList<StatNode> stats = program.getStats();
		for (DeclNode decl : decls)
			if (decl.getKind().equalsIgnoreCase("Def_decl"))
				codeString.append(decl.accept(this));
		codeString.append("int main(void) {\n");
		for (DeclNode decl : decls)
			if (decl.getKind().equalsIgnoreCase("Var_decl"))
				codeString.append(decl.accept(this));
		for (StatNode stat : stats)
			codeString.append(stat.accept(this));
		codeString.append("return 0;\n");
		codeString.append("}\n");
		return codeString.toString();
	}

	@Override
	public String visit(VarDeclNode varDecl) {
		StringBuilder codeString = new StringBuilder();
		String type = varDecl.getVarType();
		VarsNode vars = varDecl.getVars();
		codeString.append(type.toLowerCase() + " ");
		codeString.append(vars.accept(this));
		return codeString.toString();
	}

	@Override
	public String visit(VarsNode vars) {
		StringBuilder codeString = new StringBuilder();
		IdentifierNode name = vars.getName();
		VarsNode nextVars = vars.getVars();
		codeString.append(name.accept(this));
		if (nextVars != null)
			codeString.append(", " + nextVars.accept(this));
		else
			codeString.append(";\n");
		return codeString.toString();
	}

	@Override
	public String visit(IdentifierNode id) {
		return id.getIdentifier();
	}

	@Override
	public String visit(DefDeclNode defDecl) {
		StringBuilder codeString = new StringBuilder();
		IdentifierNode id = defDecl.getName();
		ArrayList<VarDeclNode> varDecls = defDecl.getVarDecls();
		ArrayList<VarDeclNode> parDecls = defDecl.getParDecls();
		BodyNode body = defDecl.getBody();
		codeString.append("void ");
		codeString.append(id.accept(this) + '(');
		for (VarDeclNode varDecl : varDecls) {
			String type = varDecl.getVarType();
			VarsNode vars = varDecl.getVars();
			ArrayList<IdentifierNode> names = vars.getNames();
			for (IdentifierNode name : names) {
				codeString.append(type.toLowerCase() + ' ');
				codeString.append(name.getIdentifier());
				if (!parDecls.isEmpty())
					codeString.append(", ");
			}
		}
		for (int i = 0; i < parDecls.size(); i++) {
			VarDeclNode parDecl = parDecls.get(i);
			String type = parDecl.getVarType();
			VarsNode vars = parDecl.getVars();
			ArrayList<IdentifierNode> names = vars.getNames();
			for (int j = 0; j < names.size(); j++) {
				IdentifierNode name = names.get(j);
				codeString.append(type.toLowerCase() + ' ');
				codeString.append('*' + name.getIdentifier());
				if (i != parDecls.size() - 1 || j != names.size() - 1)
					codeString.append(", ");
				parNames.add(name.getIdentifier());
			}
		}
		codeString.append(") ");
		codeString.append(body.accept(this));
		return codeString.toString();
	}

	@Override
	public String visit(BodyNode body) {
		StringBuilder codeString = new StringBuilder();
		ArrayList<VarDeclNode> varDecls = body.getVarDecls();
		ArrayList<StatNode> stats = body.getStats();
		codeString.append("{\n");
		int startIndex = codeString.indexOf("{");
		for (VarDeclNode varDecl : varDecls)
			codeString.append(varDecl.accept(this));
		for (StatNode stat : stats)
			codeString.append(stat.accept(this));
		codeString.append("}\n");
		for (String parName : parNames) {
			int index = codeString.indexOf(parName, startIndex);
			while (index != -1) {
				codeString.insert(index, '*');
				startIndex = index + parName.length() + 1;
				index = codeString.indexOf(parName, startIndex);
			}
		}
		return codeString.toString();
	}

	public String visit(ReadNode read) {
		StringBuilder codeString = new StringBuilder();
		ArrayList<IdentifierNode> vars = read.getVars();
		ArrayList<String> types = read.getTypes();
		codeString.append("scanf(\"");
		for (String type : types)
			if (type.equalsIgnoreCase("int"))
				codeString.append("%d");
			else if (type.equalsIgnoreCase("double"))
				codeString.append("%lf");
		codeString.append("\", ");
		for (int i = 0; i < vars.size(); i++) {
			IdentifierNode var = vars.get(i);
			codeString.append('&' + var.accept(this));
			if (i < vars.size() - 1)
				codeString.append(", ");
		}
		codeString.append(");\n");
		return codeString.toString();
	}

	public String visit(WriteNode write) {
		StringBuilder codeString1 = new StringBuilder();
		StringBuilder codeString2 = new StringBuilder();
		ArrayList<OutValueNode> outValues = write.getOutValues();
		codeString1.append("printf(\"");
		for (OutValueNode outValue : outValues) {
			if (outValue.getKind().equalsIgnoreCase("String_const"))
				codeString1.append(outValue.accept(this));
			else {
				String type = outValue.getType();
				String spec = null;
				if (type.equalsIgnoreCase("int"))
					spec = "%d";
				else if (type.equalsIgnoreCase("double"))
					spec = "%lf";
				if (spec != null) {
					codeString1.append(spec);
					codeString2.append(outValue.accept(this) + ", ");
				}
			}
		}
		codeString1.append('"');
		int index = codeString2.lastIndexOf(",");
		if (index != -1) {
			codeString2.deleteCharAt(codeString2.lastIndexOf(","));
			codeString2.deleteCharAt(codeString2.lastIndexOf(" "));
		}
		if (codeString2.length() != 0) {
			codeString1.append(", ");
			codeString1.append(codeString2);
		}
		codeString1.append(");\n");
		return codeString1.toString();
	}

	public String visit(StringConstNode outValueStr) {
		return outValueStr.getStringConst();
	}

	public String visit(AssignNode assign) {
		StringBuilder codeString = new StringBuilder();
		IdentifierNode name = assign.getName();
		ExprNode expr = assign.getExpr();
		codeString.append(name.accept(this));
		codeString.append(" = ");
		codeString.append(expr.accept(this));
		codeString.append(";\n");
		return codeString.toString();
	}

	public String visit(CallNode call) {
		StringBuilder codeString = new StringBuilder();
		IdentifierNode name = call.getName();
		ArrayList<ExprNode> exprs = call.getParams();
		ArrayList<IdentifierNode> vars = call.getVars();
		codeString.append(name.accept(this) + '(');
		for (ExprNode expr : exprs) {
			codeString.append(expr.accept(this));
			if (!vars.isEmpty())
				codeString.append(", ");
		}
		for (int i = 0; i < vars.size(); i++) {
			IdentifierNode var = vars.get(i);
			codeString.append('&' + var.accept(this));
			if (i < vars.size() - 1)
				codeString.append(", ");
		}
		codeString.append(");\n");
		return codeString.toString();
	}

	public String visit(CompStatNode compStat) {
		StringBuilder codeString = new StringBuilder();
		ArrayList<StatNode> stats = compStat.getStatements();
		codeString.append("{\n");
		for (StatNode stat : stats)
			codeString.append(stat.accept(this));
		codeString.append("}\n");
		return codeString.toString();
	}

	public String visit(IfThenNode ifThen) {
		StringBuilder codeString = new StringBuilder();
		BoolExprNode boolExpr = ifThen.getBoolExpr();
		StatNode stat = ifThen.getStat();
		codeString.append("if ");
		codeString.append('(' + boolExpr.accept(this) + ") ");
		codeString.append(stat.accept(this));
		return codeString.toString();
	}

	public String visit(IfThenElseNode ifThenElse) {
		StringBuilder codeString = new StringBuilder();
		BoolExprNode boolExpr = ifThenElse.getBoolExpr();
		StatNode stat1 = ifThenElse.getStat1();
		StatNode stat2 = ifThenElse.getStat2();
		codeString.append("if ");
		codeString.append('(' + boolExpr.accept(this) + ") ");
		codeString.append(stat1.accept(this));
		codeString.append("else ");
		codeString.append(stat2.accept(this));
		return codeString.toString();
	}

	public String visit(WhileNode whileNode) {
		StringBuilder codeString = new StringBuilder();
		BoolExprNode boolExpr = whileNode.getBoolExpr();
		StatNode stat = whileNode.getStat();
		codeString.append("while ");
		codeString.append('(' + boolExpr.accept(this) + ") ");
		codeString.append(stat.accept(this));
		return codeString.toString();
	}

	public String visit(ArithOpNode arithOp) {
		StringBuilder codeString = new StringBuilder();
		char op = arithOp.getArithOp();
		ExprNode expr1 = arithOp.getExpr1();
		ExprNode expr2 = arithOp.getExpr2();
		codeString.append(expr1.accept(this));
		codeString.append(' ' + Character.toString(op) + ' ');
		codeString.append(expr2.accept(this));
		return codeString.toString();
	}

	public String visit(UMinusNode uMinus) {
		ExprNode expr = uMinus.getExpr();
		return '-' + expr.accept(this);
	}

	public String visit(IntConstNode intConst) {
		return Integer.toString(intConst.getIntConst());
	}

	public String visit(DoubleConstNode doubleConst) {
		return Double.toString(doubleConst.getDoubleConst());
	}

	public String visit(BoolOpNode boolOp) {
		StringBuilder codeString = new StringBuilder();
		String op = boolOp.getBoolOp();
		BoolExprNode boolExpr1 = boolOp.getBoolExpr1();
		BoolExprNode boolExpr2 = boolOp.getBoolExpr2();
		codeString.append(boolExpr1.accept(this));
		codeString.append(' ' + op + ' ');
		codeString.append(boolExpr2.accept(this));
		return codeString.toString();
	}

	public String visit(NotNode not) {
		BoolExprNode boolExpr = not.getBoolExpr();
		return "!(" + boolExpr.accept(this) + ")";
	}

	public String visit(BoolValueNode boolValue) {
		return Boolean.toString(boolValue.getValue());
	}

	public String visit(RelOpNode relOp) {
		StringBuilder codeString = new StringBuilder();
		String op = relOp.getRelOp();
		ExprNode expr1 = relOp.getExpr1();
		ExprNode expr2 = relOp.getExpr2();
		codeString.append(expr1.accept(this));
		codeString.append(' ' + op + ' ');
		codeString.append(expr2.accept(this));
		return codeString.toString();
	}

}

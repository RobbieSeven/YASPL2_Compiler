package visitors;

import java.util.ArrayList;

import nodes.*;

public class SemanticVisitor implements Visitor {
	
	private SymbolTable symbolTable;
	
	public SemanticVisitor() {
		symbolTable = new SymbolTable();
	}

	public String visit(ProgramNode program) throws SemanticException {
		symbolTable.enterScope();
		program.setScope(symbolTable.getCurrentScope());
		ArrayList<DeclNode> decls = program.getDecls();
		ArrayList<StatNode> stats = program.getStats();
		for (DeclNode decl : decls)
			decl.accept(this);
		for (StatNode stat : stats)
			stat.accept(this);
		symbolTable.exitScope();
		return null;
	}

	public String visit(VarDeclNode varDecl) throws SemanticException {
		ArrayList<IdentifierNode> ids = varDecl.getVars().getNames();
		for (IdentifierNode id : ids) {
			String name = id.getIdentifier();
			if (symbolTable.probe(name))
				throw new SemanticException("Variable " + name + " already defined");
			else {
				SymbolEntry entry = new SymbolEntry("var", varDecl.getVarType());
				symbolTable.addId(name, entry);
			}
		}
		return null;
	}

	public String visit(VarsNode vars) throws SemanticException {
		ArrayList<IdentifierNode> ids = vars.getNames();
		for (IdentifierNode id : ids)
			id.accept(this);
		return null;
	}

	public String visit(IdentifierNode id) throws SemanticException {
		String name = id.getIdentifier();
		SymbolEntry entry = symbolTable.lookUp(name);
		if (entry != null) {
			id.setType(entry.getType());
			id.setEntry(entry);
		} else
			throw new SemanticException("Identifier " + name + " not defined");
		return null;
	}

	public String visit(DefDeclNode defDecl) throws SemanticException {
		String name = defDecl.getName().getIdentifier();
		if (symbolTable.probe(name))
			throw new SemanticException("Variable " + name + " already defined");
		else {
			ArrayList<VarDeclNode> varDecls = defDecl.getVarDecls();
			ArrayList<VarDeclNode> parDecls = defDecl.getParDecls();
			BodyNode body = defDecl.getBody();
			ArrayList<String> varTypes = new ArrayList<String>();
			for (VarDeclNode varDecl : varDecls)
				for (int i = 0; i < varDecl.getVars().getNames().size(); i++)
					varTypes.add(varDecl.getVarType());
			ArrayList<String> parTypes = new ArrayList<String>();
			for (VarDeclNode parDecl : parDecls)
				for (int i = 0; i < parDecl.getVars().getNames().size(); i++)
					parTypes.add(parDecl.getVarType());
			SymbolEntry entry = new SymbolEntry("def", "void", varTypes, parTypes);
			symbolTable.addId(name, entry);
			symbolTable.enterScope();
			defDecl.setScope(symbolTable.getCurrentScope());
			for (VarDeclNode varDecl : varDecls)
				varDecl.accept(this);
			for (VarDeclNode parDecl : parDecls)
				parDecl.accept(this);
			body.accept(this);
			symbolTable.exitScope();
		}
		return null;
	}

	public String visit(BodyNode body) throws SemanticException {
		ArrayList<VarDeclNode> varDecls = body.getVarDecls();
		ArrayList<StatNode> stats = body.getStats();
		for (VarDeclNode varDecl : varDecls)
			varDecl.accept(this);
		for (StatNode stat : stats)
			stat.accept(this);
		return null;
	}

	public String visit(ReadNode read) throws SemanticException {
		ArrayList<IdentifierNode> vars = read.getVars();
		ArrayList<String> types = read.getTypes();
		for (IdentifierNode var : vars)
			var.accept(this);
		if (vars.size() == types.size())
			for (int i = 0; i < vars.size(); i++) {
				IdentifierNode var = vars.get(i);
				String type = types.get(i);
				if (!var.getType().equalsIgnoreCase(type))
					throw new SemanticException("Type mismatch: type " + var.getType() + " expected for variable "
												+ var.getIdentifier() + " on read statement");
			}
		else
			throw new SemanticException("Number of types expected by read statement don't match with variables");
		read.setType("void");
		return null;
	}

	public String visit(WriteNode write) throws SemanticException {
		ArrayList<OutValueNode> outValues = write.getOutValues();
		for (OutValueNode outValue : outValues)
			outValue.accept(this);
		write.setType("void");
		return null;
	}

	public String visit(StringConstNode outValueStr) {
		outValueStr.setType("String");
		return null;
	}

	public String visit(AssignNode assign) throws SemanticException {
		IdentifierNode name = assign.getName();
		ExprNode expr = assign.getExpr();
		name.accept(this);
		expr.accept(this);
		String nameType = name.getType();
		String exprType = expr.getType();
		// if (name.getType().equalsIgnoreCase(expr.getType()))
		if ((nameType.equalsIgnoreCase("int") || nameType.equalsIgnoreCase("double"))
				&& (exprType.equalsIgnoreCase("int") || exprType.equalsIgnoreCase("double")))
			assign.setType("void");
		else
			throw new SemanticException("Type mismatch on variable " + name);
		return null;
	}
	
	public String visit(CallNode call) throws SemanticException {
		IdentifierNode name = call.getName();
		ArrayList<ExprNode> exprs = call.getParams();
		ArrayList<IdentifierNode> vars = call.getVars();
		name.accept(this);
		for (ExprNode expr : exprs)
			expr.accept(this);
		for (IdentifierNode var : vars)
			var.accept(this);
		SymbolEntry entry = symbolTable.lookUp(name.getIdentifier());
		if (entry != null) {
			if (entry.getKind().equalsIgnoreCase("def")) {
				ArrayList<String> varTypes = entry.getVarTypes();
				if (exprs.size() == varTypes.size()) {
					for (int i = 0; i < exprs.size(); i++) {
						ExprNode expr = exprs.get(i);
						String varType = varTypes.get(i);
						if (!expr.getType().equalsIgnoreCase(varType))
							throw new SemanticException("Type mismatch: parameter of type " + varType + " expected "
														+ "on function " + name.getIdentifier());
					}
					ArrayList<String> parTypes = entry.getParTypes();
					if (vars.size() == parTypes.size()) {
						for (int i = 0; i < vars.size(); i++) {
							ExprNode var = vars.get(i);
							String parType = parTypes.get(i);
							if (!var.getType().equalsIgnoreCase(parType))
								throw new SemanticException("Type mismatch: parameter of type " + parType + " expected "
															+ "on function " + name.getIdentifier());
						}
						call.setType("void");
					} else
						throw new SemanticException("Number of return values to function " + name.getIdentifier() + " "
													+ "don't match with function declaration");
				} else
					throw new SemanticException("Number of parameters to function " + name.getIdentifier() + " "
												+ "don't match with function declaration");
			} else
				throw new SemanticException("Function " + name.getIdentifier() + " already defined as variable");
		} else
			throw new SemanticException("Function " + name.getIdentifier() + " not defined");
		return null;
	}

	public String visit(CompStatNode compStat) throws SemanticException {
		ArrayList<StatNode> stats = compStat.getStatements();
		for (StatNode stat : stats)
			stat.accept(this);
		compStat.setType("void");
		return null;
	}

	public String visit(IfThenNode ifThen) throws SemanticException {
		BoolExprNode boolExpr = ifThen.getBoolExpr();
		StatNode stat = ifThen.getStat();
		boolExpr.accept(this);
		stat.accept(this);
		if (boolExpr.getType().equalsIgnoreCase("bool"))
			ifThen.setType("void");
		else
			throw new SemanticException("Boolean expression expected instead of " + boolExpr.getType() + " expr");
		return null;
	}

	public String visit(IfThenElseNode ifThenElse) throws SemanticException {
		BoolExprNode boolExpr = ifThenElse.getBoolExpr();
		StatNode stat1 = ifThenElse.getStat1();
		StatNode stat2 = ifThenElse.getStat2();
		boolExpr.accept(this);
		stat1.accept(this);
		stat2.accept(this);
		if (boolExpr.getType().equalsIgnoreCase("bool"))
			ifThenElse.setType("void");
		else
			throw new SemanticException("Boolean expression expected instead of " + boolExpr.getType() + " expr");
		return null;
	}

	public String visit(WhileNode whileNode) throws SemanticException {
		BoolExprNode boolExpr = whileNode.getBoolExpr();
		StatNode stat = whileNode.getStat();
		boolExpr.accept(this);
		stat.accept(this);
		if (boolExpr.getType().equalsIgnoreCase("bool"))
			whileNode.setType("void");
		else
			throw new SemanticException("Boolean expression expected instead of " + boolExpr.getType() + " expr");
		return null;
	}

	public String visit(ArithOpNode arithOp) throws SemanticException {
		ExprNode expr1 = arithOp.getExpr1();
		ExprNode expr2 = arithOp.getExpr2();
		expr1.accept(this);
		expr2.accept(this);
		String expr1Type = expr1.getType();
		String expr2Type = expr2.getType();
		if ((expr1Type.equalsIgnoreCase("int") || expr1Type.equalsIgnoreCase("double"))
			&& (expr2Type.equalsIgnoreCase("int") || expr2Type.equalsIgnoreCase("double")))
			if (expr1Type.equalsIgnoreCase("double") || expr2Type.equalsIgnoreCase("double"))
				arithOp.setType("double");
			else
				arithOp.setType("int");
		else
			throw new SemanticException("Type mismatch between " + expr1Type + " expr and " + expr2Type + " expr");
		return null;
	}

	public String visit(UMinusNode uMinus) throws SemanticException {
		ExprNode expr = uMinus.getExpr();
		expr.accept(this);
		String exprType = expr.getType();
		if (exprType.equalsIgnoreCase("int") || exprType.equalsIgnoreCase("double"))
			uMinus.setType(exprType);
		else
			throw new SemanticException("Type mismatch on " + exprType + " expr");
		return null;
	}

	public String visit(IntConstNode intConst) {
		intConst.setType("int");
		return null;
	}

	public String visit(DoubleConstNode doubleConst) {
		doubleConst.setType("double");
		return null;
	}

	public String visit(BoolOpNode boolOp) throws SemanticException {
		BoolExprNode boolExpr1 = boolOp.getBoolExpr1();
		BoolExprNode boolExpr2 = boolOp.getBoolExpr2();
		boolExpr1.accept(this);
		boolExpr2.accept(this);
		if (boolExpr1.getType().equalsIgnoreCase("bool") && boolExpr2.getType().equalsIgnoreCase("bool"))
			boolOp.setType("bool");
		else
			throw new SemanticException("Type mismatch between " + boolExpr1.getType() + " expr "
										+ "and " + boolExpr2.getType() + " expr");
		return null;
	}

	public String visit(NotNode not) throws SemanticException {
		BoolExprNode boolExpr = not.getBoolExpr();
		boolExpr.accept(this);
		if (boolExpr.getType().equalsIgnoreCase("bool"))
			not.setType("bool");
		return null;
	}

	public String visit(BoolValueNode boolValue) {
		boolValue.setType("bool");
		return null;
	}

	public String visit(RelOpNode relOp) throws SemanticException {
		ExprNode expr1 = relOp.getExpr1();
		ExprNode expr2 = relOp.getExpr2();
		expr1.accept(this);
		expr2.accept(this);
		String expr1Type = expr1.getType();
		String expr2Type = expr2.getType();
		if ((expr1Type.equalsIgnoreCase("int") || expr1Type.equalsIgnoreCase("double"))
			&& (expr2Type.equalsIgnoreCase("int") || expr2Type.equalsIgnoreCase("double")))
			relOp.setType("bool");
		else
			throw new SemanticException("Type mismatch between " + expr1Type + " expr and " + expr2Type + " expr");
		return null;
	}

}

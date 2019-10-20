package visitors;

import java.util.ArrayList;

import nodes.*;

public class SyntaxVisitor {
	
	private String startTag(String string) {
		return "<" + string + ">\n";
	}
	private String endTag(String string) {
		return "</" + string + ">\n";
	}
	
	private String singleTag(String tag, String attribute) {
		return "<" + tag + " attr=\"" + attribute + "\"/>\n";
	}
	
	public String visit(ProgramNode program) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(program.getKind()));
		ArrayList<DeclNode> decls = program.getDecls();
		ArrayList<StatNode> stats = program.getStats();
		parseTreeString.append(startTag("Decls"));
		for (DeclNode decl : decls)
			parseTreeString.append(decl.accept(this));
		parseTreeString.append(endTag("Decls"));
		parseTreeString.append(startTag("Stats"));
		for (StatNode stat : stats)
			parseTreeString.append(stat.accept(this));
		parseTreeString.append(endTag("Stats"));
		parseTreeString.append(endTag(program.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(VarDeclNode varDecl) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(varDecl.getKind()));
		VarsNode vars = varDecl.getVars();
		parseTreeString.append(singleTag("Type", varDecl.getVarType()));
		parseTreeString.append(vars.accept(this));
		parseTreeString.append(endTag(varDecl.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(VarsNode vars) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(vars.getKind()));
		IdentifierNode name = vars.getName();
		VarsNode nextVars = vars.getVars();
		parseTreeString.append(name.accept(this));
		if (nextVars != null)
			parseTreeString.append(nextVars.accept(this));
		parseTreeString.append(endTag(vars.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(IdentifierNode id) {
		StringBuilder parseTreeString = new StringBuilder();
		String name = id.getIdentifier();
		parseTreeString.append(singleTag(id.getKind(), name));
		return parseTreeString.toString();
	}
	
	public String visit(DefDeclNode defDecl) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(defDecl.getKind()));
		IdentifierNode id = defDecl.getName();
		ArrayList<VarDeclNode> varDecls = defDecl.getVarDecls();
		ArrayList<VarDeclNode> parDecls = defDecl.getParDecls();
		BodyNode body = defDecl.getBody();
		parseTreeString.append(id.accept(this));
		parseTreeString.append(startTag("Var_decls"));
		for (VarDeclNode varDecl : varDecls)
			parseTreeString.append(varDecl.accept(this));
		parseTreeString.append(endTag("Var_decls"));
		parseTreeString.append(startTag("Par_decls"));
		for (VarDeclNode parDecl : parDecls)
			parseTreeString.append(parDecl.accept(this));
		parseTreeString.append(endTag("Par_decls"));
		parseTreeString.append(body.accept(this));
		parseTreeString.append(endTag(defDecl.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(BodyNode body) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(body.getKind()));
		ArrayList<VarDeclNode> varDecls = body.getVarDecls();
		ArrayList<StatNode> stats = body.getStats();
		parseTreeString.append(startTag("Var_decls"));
		for (VarDeclNode varDecl : varDecls)
			parseTreeString.append(varDecl.accept(this));
		parseTreeString.append(endTag("Var_decls"));
		parseTreeString.append(startTag("Stats"));
		for (StatNode stat : stats)
			parseTreeString.append(stat.accept(this));
		parseTreeString.append(endTag("Stats"));
		parseTreeString.append(endTag(body.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(ReadNode read) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(read.getKind()));
		ArrayList<IdentifierNode> vars = read.getVars();
		ArrayList<String> types = read.getTypes();
		parseTreeString.append(startTag("Vars"));
		for (IdentifierNode var : vars)
			parseTreeString.append(var.accept(this));
		parseTreeString.append(endTag("Vars"));
		parseTreeString.append(startTag("Types"));
		for (String type : types)
			parseTreeString.append(singleTag("Type", type));
		parseTreeString.append(endTag("Types"));
		parseTreeString.append(endTag(read.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(WriteNode write) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(write.getKind()));
		ArrayList<OutValueNode> outValues = write.getOutValues();
		for (OutValueNode outValue : outValues)
			parseTreeString.append(outValue.accept(this));
		parseTreeString.append(endTag(write.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(StringConstNode outValueStr) {
		String stringConst = outValueStr.getStringConst();
		return singleTag(outValueStr.getKind(), stringConst);
	}
	
	public String visit(AssignNode assign) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(assign.getKind()));
		IdentifierNode name = assign.getName();
		ExprNode expr = assign.getExpr();
		parseTreeString.append(name.accept(this));
		parseTreeString.append(expr.accept(this));
		parseTreeString.append(endTag(assign.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(CallNode call) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(call.getKind()));
		IdentifierNode name = call.getName();
		ArrayList<ExprNode> exprs = call.getParams();
		ArrayList<IdentifierNode> vars = call.getVars();
		parseTreeString.append(name.accept(this));
		parseTreeString.append(startTag("Exprs"));
		for (ExprNode expr : exprs)
			parseTreeString.append(expr.accept(this));
		parseTreeString.append(endTag("Exprs"));
		parseTreeString.append(startTag("Vars"));
		for (IdentifierNode var : vars)
			parseTreeString.append(var.accept(this));
		parseTreeString.append(endTag("Vars"));
		parseTreeString.append(endTag(call.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(CompStatNode compStat) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(compStat.getKind()));
		ArrayList<StatNode> stats = compStat.getStatements();
		for (StatNode stat : stats)
			parseTreeString.append(stat.accept(this));
		parseTreeString.append(endTag(compStat.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(IfThenNode ifThen) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(ifThen.getKind()));
		BoolExprNode boolExpr = ifThen.getBoolExpr();
		StatNode stat = ifThen.getStat();
		parseTreeString.append(boolExpr.accept(this));
		parseTreeString.append(stat.accept(this));
		parseTreeString.append(endTag(ifThen.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(IfThenElseNode ifThenElse) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(ifThenElse.getKind()));
		BoolExprNode boolExpr = ifThenElse.getBoolExpr();
		StatNode stat1 = ifThenElse.getStat1();
		StatNode stat2 = ifThenElse.getStat2();
		parseTreeString.append(boolExpr.accept(this));
		parseTreeString.append(stat1.accept(this));
		parseTreeString.append(stat2.accept(this));
		parseTreeString.append(endTag(ifThenElse.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(WhileNode whileNode) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(whileNode.getKind()));
		BoolExprNode boolExpr = whileNode.getBoolExpr();
		StatNode stat = whileNode.getStat();
		parseTreeString.append(boolExpr.accept(this));
		parseTreeString.append(stat.accept(this));
		parseTreeString.append(endTag(whileNode.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(ArithOpNode arithOp) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(arithOp.getKind()));
		ExprNode expr1 = arithOp.getExpr1();
		ExprNode expr2 = arithOp.getExpr2();
		parseTreeString.append(expr1.accept(this));
		parseTreeString.append(expr2.accept(this));
		parseTreeString.append(endTag(arithOp.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(UMinusNode uMinus) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(uMinus.getKind()));
		ExprNode expr = uMinus.getExpr();
		parseTreeString.append(expr.accept(this));
		parseTreeString.append(endTag(uMinus.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(IntConstNode intConst) {
		StringBuilder parseTreeString = new StringBuilder();
		String constant = Integer.toString(intConst.getIntConst());
		parseTreeString.append(singleTag(intConst.getKind(), constant));
		return parseTreeString.toString();
	}
	
	public String visit(DoubleConstNode doubleConst) {
		StringBuilder parseTreeString = new StringBuilder();
		String constant = Double.toString(doubleConst.getDoubleConst());
		parseTreeString.append(singleTag(doubleConst.getKind(), constant));
		return parseTreeString.toString();
	}
	
	public String visit(BoolOpNode boolOp) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(boolOp.getKind()));
		BoolExprNode boolExpr1 = boolOp.getBoolExpr1();
		BoolExprNode boolExpr2 = boolOp.getBoolExpr2();
		parseTreeString.append(boolExpr1.accept(this));
		parseTreeString.append(boolExpr2.accept(this));
		parseTreeString.append(endTag(boolOp.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(NotNode not) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(not.getKind()));
		BoolExprNode boolExpr = not.getBoolExpr();
		parseTreeString.append(boolExpr.accept(this));
		parseTreeString.append(endTag(not.getKind()));
		return parseTreeString.toString();
	}
	
	public String visit(BoolValueNode boolValue) {
		boolean value = boolValue.getValue();
		return singleTag(boolValue.getKind(), String.valueOf(value));
	}
	
	public String visit(RelOpNode relOp) {
		StringBuilder parseTreeString = new StringBuilder();
		parseTreeString.append(startTag(relOp.getKind()));
		ExprNode expr1 = relOp.getExpr1();
		ExprNode expr2 = relOp.getExpr2();
		parseTreeString.append(expr1.accept(this));
		parseTreeString.append(expr2.accept(this));
		parseTreeString.append(endTag(relOp.getKind()));
		return parseTreeString.toString();
	}

}

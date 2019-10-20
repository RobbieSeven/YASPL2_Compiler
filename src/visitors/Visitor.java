package visitors;

import nodes.*;

public interface Visitor {
	
	public String visit(ProgramNode program) throws SemanticException;
	public String visit(VarDeclNode varDecl) throws SemanticException;
	public String visit(VarsNode vars) throws SemanticException;
	public String visit(IdentifierNode id) throws SemanticException;
	public String visit(DefDeclNode defDecl) throws SemanticException;
	public String visit(BodyNode body) throws SemanticException;
	public String visit(ReadNode read) throws SemanticException;
	public String visit(WriteNode write) throws SemanticException;
	public String visit(StringConstNode outValueStr);
	public String visit(AssignNode assign) throws SemanticException;
	public String visit(CallNode call) throws SemanticException;
	public String visit(CompStatNode compStat) throws SemanticException;
	public String visit(IfThenNode ifThen) throws SemanticException;
	public String visit(IfThenElseNode ifThenElse) throws SemanticException;
	public String visit(WhileNode whileNode) throws SemanticException;	
	public String visit(ArithOpNode arithOp) throws SemanticException;
	public String visit(UMinusNode uMinus) throws SemanticException;
	public String visit(IntConstNode intConst);
	public String visit(DoubleConstNode doubleConst);
	public String visit(BoolOpNode boolOp) throws SemanticException;
	public String visit(NotNode not) throws SemanticException;
	public String visit(BoolValueNode boolValue);
	public String visit(RelOpNode relOp) throws SemanticException;
	
}

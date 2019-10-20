package nodes;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class ArithOpNode extends ExprNode {
	
	private char arithOp;
	private ExprNode expr1;
	private ExprNode expr2;
	
	public ArithOpNode(char arithOp, ExprNode expr1, ExprNode expr2) {
		this.arithOp = arithOp;
		this.expr1 = expr1;
		this.expr2 = expr2;
	}

	@Override
	public String getKind() {
		switch (arithOp) {
			case '+':
				return "Addition";
			case '-':
				return "Subtraction";
			case '*':
				return "Multiplication";
			case '/':
				return "Division";
			default:
				return "Arith_op";
		}
	}
	
	public char getArithOp() {
		return arithOp;
	}
	
	public ExprNode getExpr1() {
		return expr1;
	}
	
	public ExprNode getExpr2() {
		return expr2;
	}
	
	@Override
	public String accept(SyntaxVisitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public void accept(SemanticVisitor visitor) throws SemanticException {
		visitor.visit(this);
	}
	
	public String accept(CodeVisitor visitor) {
		return visitor.visit(this);
	}

}

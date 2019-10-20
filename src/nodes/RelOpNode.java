package nodes;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class RelOpNode extends BoolExprNode {
	
	private String relOp;
	private ExprNode expr1;
	private ExprNode expr2;
	
	public RelOpNode(String relOp, ExprNode expr1, ExprNode expr2) {
		this.relOp = relOp;
		this.expr1 = expr1;
		this.expr2 = expr2;
	}

	@Override
	public String getKind() {
		switch (relOp) {
			case "==":
				return "Equals";
			case "!=":
				return "Not_equals";
			case ">":
				return "Greater_than";
			case ">=":
				return "Greater_equals";
			case "<":
				return "Less_than";
			case "<=":
				return "Less_equals";
			default:
				return "Rel_op";
		}
	}
	
	public String getRelOp() {
		return relOp;
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

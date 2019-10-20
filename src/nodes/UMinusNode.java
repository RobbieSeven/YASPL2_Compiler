package nodes;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class UMinusNode extends ExprNode {
	
	private ExprNode expr;
	
	public UMinusNode(ExprNode expr) {
		this.expr = expr;
	}

	@Override
	public String getKind() {
		return "U_minus";
	}
	
	public ExprNode getExpr() {
		return expr;
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

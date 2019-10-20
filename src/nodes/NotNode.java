package nodes;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class NotNode extends BoolExprNode {
	
	private BoolExprNode boolExpr;
	
	public NotNode(BoolExprNode boolExpr) {
		this.boolExpr = boolExpr;
	}

	@Override
	public String getKind() {
		return "Not";
	}
	
	public BoolExprNode getBoolExpr() {
		return boolExpr;
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

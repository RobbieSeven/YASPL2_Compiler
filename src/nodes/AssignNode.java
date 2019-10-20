package nodes;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class AssignNode extends SimpleStatNode {

	private IdentifierNode name;
	private ExprNode expr;
	
	public AssignNode(IdentifierNode name, ExprNode expr) {
		this.name = name;
		this.expr = expr;
	}
	
	@Override
	public String getKind() {
		return "Assign";
	}
	
	public IdentifierNode getName() {
		return name;
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

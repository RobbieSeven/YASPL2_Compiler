package nodes;

import visitors.CodeVisitor;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class BoolValueNode extends BoolExprNode {

	private final boolean value;
	
	public BoolValueNode(boolean value) {
		this.value = value;
	}
	
	@Override
	public String getKind() {
		return "Bool_value";
	}
	
	public boolean getValue() {
		return value;
	}
	
	@Override
	public String accept(SyntaxVisitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public void accept(SemanticVisitor visitor) {
		visitor.visit(this);
	}
	
	public String accept(CodeVisitor visitor) {
		return visitor.visit(this);
	}

}

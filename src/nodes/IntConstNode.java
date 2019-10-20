package nodes;

import visitors.CodeVisitor;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class IntConstNode extends ExprNode {

	private final int intConst;
	
	public IntConstNode(int intConst) {
		this.intConst = intConst;
	}
	
	@Override
	public String getKind() {
		return "Int_const";
	}
	
	public int getIntConst() {
		return intConst;
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

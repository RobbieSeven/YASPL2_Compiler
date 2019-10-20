package nodes;

import visitors.CodeVisitor;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class DoubleConstNode extends ExprNode {

	private final double doubleConst;
	
	public DoubleConstNode(double doubleConst) {
		this.doubleConst = doubleConst;
	}
	
	@Override
	public String getKind() {
		return "Double_const";
	}
	
	public double getDoubleConst() {
		return doubleConst;
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

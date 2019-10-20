package nodes;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class WhileNode extends SimpleStatNode {

	private BoolExprNode boolExpr;
	private StatNode stat;
	
	public WhileNode(BoolExprNode boolExpr, StatNode stat) {
		this.boolExpr = boolExpr;
		this.stat = stat;
	}
	
	@Override
	public String getKind() {
		return "While";
	}
	
	public BoolExprNode getBoolExpr() {
		return boolExpr;
	}
	
	public StatNode getStat() {
		return stat;
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
